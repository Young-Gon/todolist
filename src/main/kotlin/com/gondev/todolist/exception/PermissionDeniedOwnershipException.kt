package com.gondev.todolist.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
class PermissionDeniedOwnershipException(message: String): RuntimeException(message)
