package com.gondev.todolist.model

import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(uniqueConstraints = [UniqueConstraint(name="UK_QUESTION_NO",columnNames = ["survey_id","no"])])
@DiscriminatorColumn(name="type")
open class Question(
        var no:Int=0,
        var content:String?=null,
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name="survey_id", nullable = false)
        var survey: Survey
):BaseEntity()