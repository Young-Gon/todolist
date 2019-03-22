package com.gondev.todolist.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import javax.validation.constraints.Email

@Entity
@Table(name = "users", uniqueConstraints = [UniqueConstraint(name="UK_USER_EMAIL",columnNames = ["email"])])
data class User (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long=0,

        @Column(nullable = false)
        var name: String="",

        @Email
        @Column(nullable = false)
        var email: String="",

        @Column(nullable = false)
        @Enumerated(EnumType.STRING)
        var provider: AuthProvider=AuthProvider.local,

        @Column(nullable = false)
        var emailVerified: Boolean=false,

        @Column(nullable = false)
        var providerId: String="",

        var imageUrl: String? = null,

        @JsonIgnore
        var password: String? = null
)
