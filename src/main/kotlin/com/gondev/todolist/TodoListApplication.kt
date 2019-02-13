package com.gondev.todolist

import com.gondev.todolist.config.AppProperties
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootApplication
@EnableConfigurationProperties(AppProperties::class)
class TodoListApplication

fun main(args: Array<String>) {
    runApplication<TodoListApplication>(*args)
}

