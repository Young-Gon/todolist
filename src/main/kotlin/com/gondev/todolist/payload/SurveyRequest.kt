package com.gondev.todolist.payload

import com.gondev.todolist.model.Question
import com.gondev.todolist.model.Survey
import com.gondev.todolist.security.UserPrincipal

class SurveyRequest(
        val title: String,
        val description: String?=null,
        val questions:MutableList<Question> = arrayListOf()
) {
    fun toSurvey(userPrincipal: UserPrincipal)= Survey(title,description,user=userPrincipal.toUser())
}