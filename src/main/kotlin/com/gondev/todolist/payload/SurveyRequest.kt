package com.gondev.todolist.payload

import com.gondev.todolist.model.Survey
import com.gondev.todolist.security.UserPrincipal

class SurveyRequest(
        val id:Long=0,
        val title: String,
        val description: String?=null,
        val questions:MutableList<QuestionRequest> = arrayListOf()
) {
    fun toSurvey(userPrincipal: UserPrincipal) = Survey(
        title,
        description, user=userPrincipal.toUser()
    ).apply {
        questions=this@SurveyRequest.questions.map { request ->
            request.toQuestion(this)
        }.toMutableList()
    }
}