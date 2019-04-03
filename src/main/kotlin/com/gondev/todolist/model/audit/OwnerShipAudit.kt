package com.gondev.todolist.model.audit

import com.gondev.todolist.model.User
import org.springframework.data.annotation.CreatedBy
import javax.persistence.ForeignKey
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class OwnerShipAudit(
        @CreatedBy
        @ManyToOne
        @JoinColumn(
                name="user_id",
                foreignKey = ForeignKey(name = "fk_user"),
                nullable = false,
                updatable = false
        )
        var user: User?=null
): BaseAudit()
