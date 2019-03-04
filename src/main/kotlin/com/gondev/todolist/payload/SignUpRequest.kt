package com.gondev.todolist.payload

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

/**
 * Created by rajeevkumarsingh on 02/08/17.
 */

data class SignUpRequest (
    @NotBlank
    var name: String,

    @NotBlank
    @Email
    var email: String,

    @NotBlank
    var password: String
)
