package com.gondev.todolist.controller

import com.gondev.todolist.model.Objective
import com.gondev.todolist.model.ObjectiveItem
import com.gondev.todolist.model.Subjective
import com.gondev.todolist.payload.ApiResponse
import com.gondev.todolist.payload.SubjectiveRequest
import com.gondev.todolist.payload.SurveyRequest
import com.gondev.todolist.repository.QuestionRepository
import com.gondev.todolist.repository.SurveyRepository
import com.gondev.todolist.security.CurrentUser
import com.gondev.todolist.security.UserPrincipal
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.*
import javax.validation.Valid

@RestController
class SurveyController(
        private val surveyRepository: SurveyRepository,
        private val questionRepository: QuestionRepository
) {

    @PostMapping("/survey")
    @PreAuthorize("hasRole('USER')")
    fun addSurvey(@CurrentUser userPrincipal: UserPrincipal, @Valid @RequestBody survey: SurveyRequest) =
            surveyRepository.save(survey.toSurvey(userPrincipal)).apply {
                val location = ServletUriComponentsBuilder
                        .fromCurrentRequest().path("/{surveyId}")
                        .buildAndExpand(id).toUri()

                ResponseEntity.created(location)
                        .body(ApiResponse(true, "설문지 추가 완료!"));
            }

    @GetMapping("/survey")
    @PreAuthorize("hasRole('USER')")
    fun getSurvey(@CurrentUser userPrincipal: UserPrincipal) =
            surveyRepository.findAllByUserId(userPrincipal.id)

    @PostMapping("/survey/{surveyId}/question")
    @PreAuthorize("hasRole('USER')")
    fun addQuestion(@CurrentUser userPrincipal: UserPrincipal, @PathVariable surveyId: Long, @Valid @RequestBody question: SubjectiveRequest) =
        surveyRepository.findById(surveyId).map {
            if(it.user.id!=userPrincipal.id)
                throw Exception("해당 설문지의 소유자가 아닙니다")

            val savedQuestion=questionRepository.save( question.toQuestion(it))

            val location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{questionId}")
                    .buildAndExpand(savedQuestion.id).toUri()

            ResponseEntity.created(location)
                    .body(ApiResponse(true, "질문 추가 완료!"));

        }.orElseThrow{
            NoSuchElementException("설문지를 찾을 수 없습니다");
        }

    @PutMapping("/survey/{surveyId}/question/{questionId}/Objective")
    @PreAuthorize("hasRole('USER')")
    fun modifyQuestionTypeToObject(@CurrentUser userPrincipal: UserPrincipal,@PathVariable surveyId: Long,@PathVariable questionId: Long) =
            modifyQuestionType(userPrincipal,surveyId,questionId,"Objective")


    @PutMapping("/survey/{surveyId}/question/{questionId}/Subjective")
    @PreAuthorize("hasRole('USER')")
    fun modifyQuestionTypeToSubject(@CurrentUser userPrincipal: UserPrincipal,@PathVariable surveyId: Long,@PathVariable questionId: Long) =
            modifyQuestionType(userPrincipal,surveyId,questionId,"Subjective")

    private fun modifyQuestionType(userPrincipal: UserPrincipal, surveyId: Long, questionId: Long, type: String)=
        questionRepository.findById(questionId).map {
            if(it.survey.id!=surveyId || it.survey.user.id!=userPrincipal.id)
                throw Exception("해당 설문지의 소유자가 아닙니다")

            val question = when (type) {
                "Objective" -> {
                    val objective = Objective(it).apply {
                        items.add(ObjectiveItem(1, "항목 1", this))
                        items.add(ObjectiveItem(2, "항목 2", this))
                    }
                    System.out.print(objective.id)
                    questionRepository.save(objective)
                }
                "Subjective" ->
                    questionRepository.save(Subjective(it).apply {
                        answer = ""
                    })
                else ->
                    questionRepository.save(it)
            }

            ResponseEntity.ok()
                    .body(ApiResponse(true, "질문 타입 변경 완료!"));
        }.orElseThrow{
            NoSuchElementException("설문지를 찾을 수 없습니다");
        }
}