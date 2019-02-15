package com.gondev.todolist.controller


import java.net.NetworkInterface
import java.security.SecureRandom
import java.time.Instant
import java.util.Enumeration

/**
 * Distributed Sequence Generator.
 * Inspired by Twitter snowflake: https://github.com/twitter/snowflake/tree/snowflake-2010
 *
 * This class should be used as a Singleton.
 * Make sure that you saveNew and reuse a Single instance of SequenceGenerator per machine in your distributed system cluster.
 */
class SequenceGenerator {

    private val machineId: Int

    private var lastTimestamp = -1L
    private var sequence = 0L

    // Create Snowflake with a machineId
    constructor(machineId: Int) {
        if (machineId < 0 || machineId > maxMachineId) {
            throw IllegalArgumentException(String.format("MachineId must be between %d and %d", 0, maxMachineId))
        }
        this.machineId = machineId
    }

    // Let Snowflake generate a machineId
    constructor() {
        this.machineId = createMachineId()
    }


    fun nextId(): Long {
        var currentTimestamp = timestamp()

        synchronized(this) {
            if (currentTimestamp < lastTimestamp) {
                throw IllegalStateException("Invalid System Clock!")
            }

            if (currentTimestamp == lastTimestamp) {
                sequence = sequence + 1 and maxSequence
                if (sequence == 0L) {
                    // Sequence Exhausted, wait till next millisecond.
                    currentTimestamp = waitNextMillis(currentTimestamp)
                }
            } else {
                // reset sequence for next millisecond
                sequence = 0
            }

            lastTimestamp = currentTimestamp
        }

        var id = currentTimestamp shl TOTAL_BITS - EPOCH_BITS
        id = id or (machineId shl TOTAL_BITS - EPOCH_BITS - MACHINE_ID_BITS).toLong()
        id = id or sequence
        return id
    }

    // Block and wait till next millisecond
    private fun waitNextMillis(currentTimestamp: Long): Long {
        var currentTimestampVal = currentTimestamp
        while (currentTimestampVal == lastTimestamp) {
            currentTimestampVal = timestamp()
        }
        return currentTimestampVal
    }

    private fun createMachineId(): Int {
        var machineId: Int
        try {
            val sb = StringBuilder()
            val networkInterfaces = NetworkInterface.getNetworkInterfaces()
            while (networkInterfaces.hasMoreElements()) {
                val networkInterface = networkInterfaces.nextElement()
                val mac = networkInterface.hardwareAddress
                if (mac != null) {
                    for (i in mac.indices) {
                        sb.append(String.format("%02X", mac[i]))
                    }
                }
            }
            machineId = sb.toString().hashCode()
        } catch (ex: Exception) {
            machineId = SecureRandom().nextInt()
        }

        machineId = machineId and maxMachineId
        return machineId
    }

    companion object {
        private val TOTAL_BITS = 64
        private val EPOCH_BITS = 42
        private val MACHINE_ID_BITS = 10
        private val SEQUENCE_BITS = 12

        private val maxMachineId = (Math.pow(2.0, MACHINE_ID_BITS.toDouble()) - 1).toInt()
        private val maxSequence = (Math.pow(2.0, SEQUENCE_BITS.toDouble()) - 1).toLong()

        // Custom Epoch (January 1, 2015 Midnight UTC = 2015-01-01T00:00:00Z)
        private val CUSTOM_EPOCH = 1420070400000L


        // Get current timestamp in milliseconds, adjust for the custom epoch.
        private fun timestamp(): Long {
            return Instant.now().toEpochMilli() - CUSTOM_EPOCH
        }
    }
}