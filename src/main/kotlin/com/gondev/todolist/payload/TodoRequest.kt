package com.gondev.todolist.payload

import javax.validation.constraints.NotBlank

data class TodoRequest (
        @NotBlank
        var content: String
)