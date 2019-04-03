package com.gondev.todolist.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class ResourceNotFoundException(message: String) : RuntimeException(message){
    constructor(
            resourceName: String,
            fieldName: String,
            fieldValue: Any
    ):this("$resourceName not found with $fieldName : '$fieldValue'")
}
