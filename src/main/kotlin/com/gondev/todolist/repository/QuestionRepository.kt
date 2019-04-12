package com.gondev.todolist.repository

import com.gondev.todolist.model.Question
import com.gondev.todolist.model.Survey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface QuestionRepository:JpaRepository<Question, Long> {
    fun countBySurvey(survey: Survey): Int

    @Modifying
    @Transactional
    @Query("UPDATE Question q set q.no = q.no + 1 where q.no >= :index")
    fun updateIndex(index: Int)
}
