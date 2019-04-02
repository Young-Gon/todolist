package com.gondev.todolist.payload

import com.gondev.todolist.model.Question
import com.gondev.todolist.model.Survey

open class QuestionRequest(
        val id:Long=0,
        var no:Int=0,
        val content:String?=null
) {
    open fun toQuestion(survey: Survey)= Question(id, no, content, survey)
}