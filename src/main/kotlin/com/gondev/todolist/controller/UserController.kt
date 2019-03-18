package com.gondev.todolist.controller

import com.gondev.todolist.exception.ResourceNotFoundException
import com.gondev.todolist.model.User
import com.gondev.todolist.repository.UserRepository
import com.gondev.todolist.security.CurrentUser
import com.gondev.todolist.security.UserPrincipal
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
        private val userRepository: UserRepository
) {

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    fun getCurrentUser(@CurrentUser userPrincipal: UserPrincipal): User =
            userRepository.findById(userPrincipal.id)
            .orElseThrow { ResourceNotFoundException("User", "id", userPrincipal.id) }
}
