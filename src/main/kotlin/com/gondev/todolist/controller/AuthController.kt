package com.gondev.todolist.controller

import com.gondev.todolist.exception.BadRequestException
import com.gondev.todolist.model.AuthProvider
import com.gondev.todolist.payload.ApiResponse
import com.gondev.todolist.payload.AuthResponse
import com.gondev.todolist.payload.LoginRequest
import com.gondev.todolist.payload.SignUpRequest
import com.gondev.todolist.repository.UserRepository
import com.gondev.todolist.repository.create
import com.gondev.todolist.security.TokenProvider
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.validation.Valid

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
        if (userRepository.existsByEmailAndProvider(signUpRequest.email,AuthProvider.local)) {
            throw BadRequestException("Email address already in use.")
        }

        val result = userRepository.create {
            name = signUpRequest.name
            email = signUpRequest.email
            password = passwordEncoder.encode(signUpRequest.password)
            provider = AuthProvider.local
        }

        val location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .build().toUri()

        return ResponseEntity.created(location)
                .body(ApiResponse(true, "User registered successfully@"))
    }
}
