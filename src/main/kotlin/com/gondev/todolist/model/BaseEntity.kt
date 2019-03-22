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
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id:Long=0,

        @CreatedDate
        @Column(name = "create_at",nullable = false,updatable = false)
        @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter::class)
        var createAt: LocalDateTime=LocalDateTime.now(),

        @LastModifiedDate
        @Column(name = "modify_at",nullable = false)
        @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter::class)
        var modifyAt: LocalDateTime=LocalDateTime.now()
)