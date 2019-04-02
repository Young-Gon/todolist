package com.gondev.todolist.model

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("subjective")
class Subjective(
        id:Long=0,
        no:Int=0,
        content:String?=null,
        survey: Survey,
        var answer:String?=null
): Question(id, no, content, survey) {
    constructor(question: Question) : this(
            id=question.id,
            no = question.no,
            content = question.content,
            survey = question.survey
    )

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }
}