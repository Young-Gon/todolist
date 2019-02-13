package com.gondev.todolist.controller

import com.gondev.todolist.exception.BadRequestException
import com.gondev.todolist.model.AuthProvider
import com.gondev.todolist.model.User
import com.gondev.todolist.payload.ApiResponse
import com.gondev.todolist.payload.AuthResponse
import com.gondev.todolist.payload.LoginRequest
import com.gondev.todolist.payload.SignUpRequest
import com.gondev.todolist.repository.UserRepository
import com.gondev.todolist.security.TokenProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

import javax.validation.Valid
import java.net.URI

@RestController
@RequestMapping("/auth")
class AuthController(
        private val authenticationManager: AuthenticationManager,
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder,
        private val tokenProvider: TokenProvider
) {

    @PostMapping("/login")
    fun authenticateUser(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<*> {

        val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                        loginRequest.email,
                        loginRequest.password
                )
        )

        SecurityContextHolder.getContext().authentication = authentication

        val token = tokenProvider.createToken(authentication)
        return ResponseEntity.ok(AuthResponse(token))
    }

    @PostMapping("/signup")
    fun registerUser(@Valid @RequestBody signUpRequest: SignUpRequest): ResponseEntity<*> {
        if (userRepository.existsByEmail(signUpRequest.email!!)!!) {
            throw BadRequestException("Email address already in use.")
        }

        // Creating user's account
        val user = User()
        user.name = signUpRequest.name
        user.email = signUpRequest.email
        user.password = signUpRequest.password
        user.provider = AuthProvider.local

        user.password = passwordEncoder.encode(user.password)

        val result = userRepository.save(user)

        val location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.id!!).toUri()

        return ResponseEntity.created(location)
                .body(ApiResponse(true, "User registered successfully@"))
    }
}
