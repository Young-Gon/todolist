package com.gondev.todolist

import com.gondev.todolist.model.AuthProvider
import com.gondev.todolist.repository.TodoRepository
import com.gondev.todolist.repository.UserRepository
import com.gondev.todolist.repository.create
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.junit.jupiter.SpringExtension
import javax.transaction.Transactional

@ExtendWith(SpringExtension::class)
@DataJpaTest
class JPATests(
) {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var todoRepository: TodoRepository

    @Test
    @Transactional
    fun userRepositoryTest() {
        val user=userRepository.create {
            provider = AuthProvider.local
            name = "test"
            email = "email@user.com"
        }
        assertTrue(user.name=="test")
        assertTrue(userRepository.existsByEmailAndProvider("email@user.com", AuthProvider.local))

        val todo=todoRepository.create {
            this.user=user
            content="content"
        }

        assertTrue(todo.user==user)

        val todoList=todoRepository.findAllByUserId(PageRequest.of(0,10),user.id)
        assertTrue(todoList.size==1)
        todoList.map {
            assertTrue(it.content=="content")
        }

    }
}