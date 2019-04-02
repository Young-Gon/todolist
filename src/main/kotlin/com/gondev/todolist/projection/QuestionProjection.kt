package com.gondev.todolist.projection

interface QuestionProjection {

    fun getId(): Long

    fun getNo(): Int

    fun getContent(): String?
}