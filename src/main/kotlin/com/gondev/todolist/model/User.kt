package com.gondev.todolist.model

import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

@Entity
@Table(name = "users", uniqueConstraints = [UniqueConstraint(columnNames = ["email"])])
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var name: String? = null,

    @Email
    @Column(nullable = false)
    var email: String? = null,

    var imageUrl: String? = null,

    @Column(nullable = false)
    var emailVerified: Boolean? = false,

    @JsonIgnore
    var password: String? = null,

    @NotNull
    @Enumerated(EnumType.STRING)
    var provider: AuthProvider? = null,

    var providerId: String? = null
)
