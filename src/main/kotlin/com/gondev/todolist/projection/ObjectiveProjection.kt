package com.gondev.todolist.projection

interface ObjectiveProjection: QuestionProjection {

    fun getItem(): List<ObjectiveItemProjection>
}