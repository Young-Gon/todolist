package com.gondev.todolist.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.gondev.todolist.model.audit.BaseAudit
import javax.persistence.*

@Entity
@Table(uniqueConstraints = [UniqueConstraint(name = "UK_OBJECTIVE_NO", columnNames = ["question_id", "no"])])
data class ObjectiveItem(
        var no:Int,

        var name: String,

        @JsonIgnore
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "question_id", nullable = false)
        var question: Question
):BaseAudit()
