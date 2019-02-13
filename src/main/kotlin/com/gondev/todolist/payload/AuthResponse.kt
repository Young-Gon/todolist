package com.gondev.todolist.payload

data class AuthResponse(var accessToken: String?, var tokenType: String = "Bearer")
