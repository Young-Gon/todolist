package com.gondev.todolist.security

import com.gondev.todolist.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User

data class UserPrincipal(
        val id: Long,
        val email: String,
        private val password: String?,
        private val authorities: Collection<GrantedAuthority>,
        private val attributes: MutableMap<String, Any>? = null
) : OAuth2User, UserDetails {

    constructor(user: User, attributes: MutableMap<String, Any>?=null) : this(
            user.id,
            user.email,
            user.password,
            listOf(SimpleGrantedAuthority("ROLE_USER")),
            attributes
    )

    override fun getName() = id.toString()

    override fun getPassword(): String? = password

    override fun getUsername(): String = email

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

    override fun getAuthorities(): Collection<GrantedAuthority> = authorities

    override fun getAttributes(): MutableMap<String, Any>? = attributes
}
