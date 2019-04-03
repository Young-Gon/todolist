package com.gondev.todolist.model.audit

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*


@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
@JsonIgnoreProperties(value = ["createdAt", "updatedAt"], allowGetters = true)
open class BaseAudit(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id:Long=0,

        @CreatedDate
        @Column(name = "create_at",nullable = false,updatable = false)
        @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter::class)
        var createAt: LocalDateTime?=null,

        @LastModifiedDate
        @Column(name = "modify_at",nullable = false)
        @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter::class)
        var modifyAt: LocalDateTime?=null
)
