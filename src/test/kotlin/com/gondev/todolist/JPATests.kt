package com.gondev.todolist

import com.gondev.todolist.model.*
import com.gondev.todolist.repository.SurveyRepository
import com.gondev.todolist.repository.TodoRepository
import com.gondev.todolist.repository.UserRepository
import com.gondev.todolist.repository.create
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
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

    @Autowired
    lateinit var surveyRepository: SurveyRepository

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


        val survey1=surveyRepository.save(Survey(title="설문지 명",
                user = user,
                description = "설문 설명").apply{
            this.questions = mutableListOf(
                Subjective(this).apply {
                    this.no = 1
                    this.content = "주관식 질문 1"
                },
                Objective(this).apply {
                    this.no = 2
                    this.content = "객관식 질문 2"
                    this.items = mutableListOf(
                        ObjectiveItem(1, "항목1",this),
                        ObjectiveItem(2, "항목2",this)
                    )
                }
            )
        })
        /*surveyRepository.save(Survey(
                user = user,
                title="설문지 명",
                description = "설문 설명",
                questions = mutableListOf(
                        Subjective(
                                no=1,
                                content = "주관식 질문 1"
                        ),
                        Objective(
                                no=2,
                                content = "객관식 질문 2",
                                items = mutableListOf(
                                        ObjectiveItem(1, "항목1"),
                                        ObjectiveItem(2, "항목2")
                                )
                        )
                )
        ))*/

        val survey=surveyRepository.findFirstByUser(user)
        assertNotNull(survey)
        if(survey==null)
            return;

        assertTrue(survey.user==user)
        assertTrue(survey.title=="설문지 명")
        assertTrue(survey.description=="설문 설명")
        assertTrue(survey.questions.size==2)
        assertTrue(survey.questions[0].no==1)
        assertTrue(survey.questions[0] is Subjective)
        assertTrue(survey.questions[1].no==2)
        assertTrue(survey.questions[1] is Objective)
        assertTrue((survey.questions[1] as Objective).items.size==2)
        assertTrue((survey.questions[1] as Objective).items[0].no==1)
        assertTrue((survey.questions[1] as Objective).items[0].name=="항목1")

        /*val todo=todoRepository.create {
            this.user=user
            content="content"
        }

        assertTrue(todo.user==user)

        val todoList=todoRepository.findAllByUserId(PageRequest.of(0,10),user.id)
        assertTrue(todoList.size==1)
        todoList.map {
            assertTrue(it.content=="content")
        }*/



    }
}