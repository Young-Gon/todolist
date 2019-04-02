package com.gondev.todolist.payload

import com.gondev.todolist.model.Subjective
import com.gondev.todolist.model.Survey

class SubjectiveRequest(
        id:Long=0,
        no:Int,
        content:String,
        var answer:String?=null
):QuestionRequest(id, no, content) {

    override fun toQuestion(survey: Survey) = Subjective(id, no,content,survey,answer)
}