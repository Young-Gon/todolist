package com.gondev.todolist.projection

interface ObjectiveItemProjection {
    fun getId(): Long

    fun getNo(): Int

    fun getName(): String
}
