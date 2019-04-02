package com.gondev.todolist.projection

import com.gondev.todolist.model.User

interface SurveyProjection {

    fun getId(): Long

    fun getTitle(): String

    fun getDescription(): String?

    fun getQuestions(): List<QuestionProjection>

    fun getUser(): User
}