package com.gondev.todolist.model

import javax.persistence.*

@Entity
@Table(uniqueConstraints = [UniqueConstraint(name="UK_OBJECTIVE_NO",columnNames = ["objective_id","no"])])
class ObjectiveItem(
        var no:Int,
        var name: String,
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name="objective_id", nullable = false)
        var objective: Objective
):BaseEntity()