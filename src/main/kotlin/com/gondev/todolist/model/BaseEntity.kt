package com.gondev.todolist.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class BaseEntity(
        @get:Id
        @get:GeneratedValue(strategy = GenerationType.AUTO)
        var id:Long=0,

        @get:CreatedDate
        @get:Column(name = "create_at",nullable = false,updatable = false)
        @get:Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter::class)
        var createAt: LocalDateTime=LocalDateTime.now(),

        @get:LastModifiedDate
        @get:Column(name = "modify_at",nullable = false)
        @get:Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter::class)
        var modifyAt: LocalDateTime=LocalDateTime.now()
)