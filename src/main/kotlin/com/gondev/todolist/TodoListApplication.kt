package com.gondev.todolist

import com.gondev.todolist.config.AppProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "securityAuditorAware")
@EnableConfigurationProperties(AppProperties::class)
class TodoListApplication

fun main(args: Array<String>) {
    runApplication<TodoListApplication>(*args)
}

