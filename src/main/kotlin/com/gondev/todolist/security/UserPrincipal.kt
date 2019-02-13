package com.gondev.todolist.security

import com.gondev.todolist.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User
import java.util.Collections

data class UserPrincipal(
        val id: Long,
        val email: String,
        private val password: String?,
        private val authorities: Collection<GrantedAuthority>
) : OAuth2User, UserDetails {

    private var attributes: MutableMap<String, Any>? = null

    override fun getName() = id.toString()

    override fun getPassword(): String? = password

    override fun getUsername(): String = email

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

    override fun getAuthorities(): Collection<GrantedAuthority> = authorities

    override fun getAttributes(): MutableMap<String, Any>? = attributes

    companion object {

        fun create(user: User): UserPrincipal = UserPrincipal(
                user.id!!,
                user.email!!,
                user.password,
                listOf<GrantedAuthority>(SimpleGrantedAuthority("ROLE_USER"))
        )

        fun create(user: User, attributes: MutableMap<String, Any>): UserPrincipal =
                UserPrincipal.create(user).apply {
            this.attributes = attributes
        }
    }
}
