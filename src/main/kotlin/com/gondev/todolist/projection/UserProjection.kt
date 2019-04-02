package com.gondev.todolist.projection

interface UserProjection {
    fun getId(): Long
    fun getName(): String
    fun getEmail(): String
    fun getImageUrl(): String
}
