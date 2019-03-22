package com.gondev.todolist.repository

import com.gondev.todolist.model.Survey
import com.gondev.todolist.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SurveyRepository:JpaRepository<Survey,Long> {

    fun findFirstByUser(user:User): Survey?
}