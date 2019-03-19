package com.gondev.todolist.controller

import com.gondev.todolist.exception.ResourceNotFoundException
import com.gondev.todolist.payload.TodoRequest
import com.gondev.todolist.repository.TodoRepository
import com.gondev.todolist.repository.UserRepository
import com.gondev.todolist.repository.create
import com.gondev.todolist.security.CurrentUser
import com.gondev.todolist.security.UserPrincipal
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.ResourceAccessException
import javax.validation.Valid

@RestController
class TodoListController(
        private val userRepository: UserRepository,
        private val todoRepository: TodoRepository
) {
    @PostMapping("/todolist")
    @PreAuthorize("hasRole('USER')")
    fun addTodo(@CurrentUser userPrincipal: UserPrincipal, @Valid @RequestBody todo: TodoRequest) =
            todoRepository.create {
                user = userRepository.findById(userPrincipal.id)
                        .orElseThrow {
                            ResourceNotFoundException("User", "id", userPrincipal.id)
                        }

                content = todo.content
            }

    @GetMapping("/todolist")
    @PreAuthorize("hasRole('USER')")
    fun getTodoList(@CurrentUser userPrincipal: UserPrincipal,
                    @PageableDefault(size = 10, sort = ["id"], direction = Sort.Direction.DESC) pageable: Pageable) =
            todoRepository.findAllByUserId(pageable, userPrincipal.id)

    @DeleteMapping("/todolist/{todoItemId}")
    @PreAuthorize("hasRole('USER')")
    fun deleteTodoList(@CurrentUser userPrincipal: UserPrincipal,
                       @PathVariable todoItemId: Long) =
            todoRepository.findByIdAndUserId(todoItemId, userPrincipal.id)?.let {
                todoRepository.delete(it)
                ResponseEntity.ok(it)
            }
                    ?: throw ResourceAccessException("user ${userPrincipal.email} is not owner of required item(${todoItemId})")

    @DeleteMapping("/todolist")
    @PreAuthorize("hasRole('USER')")
    fun deleteTodoList(@CurrentUser userPrincipal: UserPrincipal)=
            todoRepository.deleteByUserId(userPrincipal.id)
}
