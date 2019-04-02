package com.gondev.todolist.model

import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import javax.persistence.*

@Entity
@DiscriminatorValue("objective")
class Objective(
        id:Long=0,
        no:Int=0,
        content:String?=null,
        survey: Survey,
        @OneToMany(mappedBy = "objective", cascade = [CascadeType.ALL], fetch = FetchType.EAGER, orphanRemoval = true)
        @Fetch(FetchMode.SELECT)
        var items: MutableList<ObjectiveItem> = mutableListOf()
):Question(id,no,content,survey) {

        constructor(question: Question) : this (
                id = question.id,
                no = question.no,
                content = question.content,
                survey = question.survey
        )

        override fun equals(other: Any?): Boolean {
                return super.equals(other)
        }
}