package com.gondev.todolist.config

import com.gondev.todolist.model.User
import com.gondev.todolist.security.UserPrincipal
import org.springframework.data.domain.AuditorAware
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.*

@Component
class SecurityAuditorAware: AuditorAware<User> {

    override fun getCurrentAuditor(): Optional<User> {
        val authentication = SecurityContextHolder.getContext().authentication
        if (!authentication.isAuthenticated || authentication is AnonymousAuthenticationToken) {
            return Optional.empty()
        }
        val userPrincipal= authentication.principal as UserPrincipal

        return Optional.ofNullable(userPrincipal.toUser())
    }
}
