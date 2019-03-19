package com.gondev.todolist.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "todo")
data class TodoEntity(
        @get:Basic
        @get:ManyToOne
        @get:JoinColumn(foreignKey = ForeignKey(name = "fk_todo_user"))
        @get:JsonIgnore
        var user: User? = null,

        @get:Basic
        @get:Column(name = "content")
        var content: String? = null
) : BaseEntity()
