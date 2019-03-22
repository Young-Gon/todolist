package com.gondev.todolist.controller

import com.gondev.todolist.payload.SurveyRequest
import com.gondev.todolist.repository.SurveyRepository
import com.gondev.todolist.repository.UserRepository
import com.gondev.todolist.security.CurrentUser
import com.gondev.todolist.security.UserPrincipal
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class SurveyController(
        private val userRepository: UserRepository,
        private val surveyRepository: SurveyRepository
) {

    @PostMapping("/survey")
    @PreAuthorize("hasRole('USER')")
    fun addTodo(@CurrentUser userPrincipal: UserPrincipal, @Valid @RequestBody surveyRequest: SurveyRequest) =
            surveyRepository.save(surveyRequest.toSurvey(userPrincipal))
}