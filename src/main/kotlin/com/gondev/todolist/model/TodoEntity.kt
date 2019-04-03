package com.gondev.todolist.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.gondev.todolist.model.audit.BaseAudit
import javax.persistence.*

@Entity
@Table(name = "todo")
data class TodoEntity(
        @ManyToOne
        @JoinColumn(foreignKey = ForeignKey(name = "fk_todo_user"))
        @JsonIgnore
        var user: User? = null,

        @Column(name = "content")
        var content: String? = null
) : BaseAudit()
