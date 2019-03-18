package com.gondev.todolist.security

import com.gondev.todolist.exception.ResourceNotFoundException
import com.gondev.todolist.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Created by rajeevkumarsingh on 02/08/17.
 */

@Service
class CustomUserDetailsService(
        val userRepository:UserRepository
) : UserDetailsService {

    @Transactional
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails =
        userRepository.findByEmail(email)
            .map { user -> UserPrincipal(user) }
            .orElseThrow { UsernameNotFoundException("User not found with email : $email") }


    @Transactional
    fun loadUserById(id: Long): UserPrincipal =
        userRepository.findById(id)
            .map { user -> UserPrincipal(user)}
            .orElseThrow { ResourceNotFoundException("User", "id", id) }
}
