package com.gondev.todolist.repository

import com.gondev.todolist.model.Survey
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SurveyRepository:JpaRepository<Survey, Long> {

    fun findAllByUserId(userId: Long, pageable: Pageable): Page<Survey>
}
