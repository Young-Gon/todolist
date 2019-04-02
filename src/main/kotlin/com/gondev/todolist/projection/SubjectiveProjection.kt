package com.gondev.todolist.projection

interface SubjectiveProjection: QuestionProjection {

    fun getAnswer(): String?
}