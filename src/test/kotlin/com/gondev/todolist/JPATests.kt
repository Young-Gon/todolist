package com.gondev.todolist

import com.gondev.todolist.model.ObjectiveItem
import com.gondev.todolist.model.Question
import com.gondev.todolist.model.QuestionType
import com.gondev.todolist.model.Survey
import com.gondev.todolist.repository.SurveyRepository
import com.gondev.todolist.repository.TodoRepository
import com.gondev.todolist.repository.UserRepository
import com.gondev.todolist.repository.create
import com.gondev.todolist.security.oauth2.AuthProvider
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

        val survey1=surveyRepository.save(Survey(
                title="설문지 명",
                description = "설문 설명").apply{
            this.questions = mutableListOf(
                Question(
                        no = 1,
                        type = QuestionType.Subjective,
                        content = "주관식 질문 1",
                        survey = this
                ),
                Question(
                        no = 2,
                        content = "객관식 질문 2",
                        type = QuestionType.Objective,
                        survey = this
                ).apply {
                    this.objectiveItems = mutableListOf(
                        ObjectiveItem(1, "항목1",this),
                        ObjectiveItem(2, "항목2",this)
                    )
                }
            )
        })

    }
}
