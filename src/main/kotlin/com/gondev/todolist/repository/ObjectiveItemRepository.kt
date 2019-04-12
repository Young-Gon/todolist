package com.gondev.todolist.repository

import com.gondev.todolist.model.ObjectiveItem
import com.gondev.todolist.model.Question
import org.springframework.data.jpa.repository.JpaRepository

interface ObjectiveItemRepository: JpaRepository<ObjectiveItem, Long> {
    fun countByQuestion(question: Question): Int
}
