package com.gondev.todolist.controller

import com.gondev.todolist.exception.BadRequestException
import com.gondev.todolist.exception.PermissionDeniedOwnershipException
import com.gondev.todolist.exception.ResourceNotFoundException
import com.gondev.todolist.model.ObjectiveItem
import com.gondev.todolist.model.Question
import com.gondev.todolist.model.QuestionType
import com.gondev.todolist.model.Survey
import com.gondev.todolist.payload.ApiResponse
import com.gondev.todolist.repository.*
import com.gondev.todolist.security.CurrentUser
import com.gondev.todolist.security.UserPrincipal
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/survey")
class SurveyController(
        private val surveyRepository: SurveyRepository,
        private val questionRepository: QuestionRepository,
        private val objectiveItemRepository: ObjectiveItemRepository
) {

    /**
     * 설문 만들기
     */
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    fun createSurvey()= surveyRepository.save()

    /**
     * 내가 만든 설문 리스트
     */
    @GetMapping("/my")
    @PreAuthorize("hasRole('USER')")
    fun getMySurvey(
            @CurrentUser
            userPrincipal: UserPrincipal,

            @PageableDefault(size = 10, sort = ["id"], direction = Sort.Direction.DESC)
            pageable: Pageable
    )= surveyRepository.findAllByUserId(userPrincipal.id, pageable)

    /**
     * 전체 설문 리스트
     */
    @GetMapping
    fun getSurvey(
            @PageableDefault(size = 10, sort = ["id"], direction = Sort.Direction.DESC)
            pageable: Pageable
    )= surveyRepository.findAllBy(pageable)

    /**
     * 설문 상세
     */
    @GetMapping("/{surveyId}")
    @PreAuthorize("hasRole('USER')")
    fun getSurveyDetail(
            @PathVariable
            surveyId: Long,

            @PageableDefault(size = 10, sort = ["id"], direction = Sort.Direction.DESC)
            pageable: Pageable
    )= surveyRepository.findById(surveyId).orElseThrow {
        throw ResourceNotFoundException("설문지($surveyId)를 찾을 수 없습니다.")
    }

    /**
     * 설문 수정
     */
    @PutMapping
    @PreAuthorize("hasRole('USER')")
    fun modifySurvey(
            @CurrentUser
            userPrincipal: UserPrincipal,

            @Valid
            @RequestBody
            request: Survey
    )=
            if(surveyRepository.existsById(request.id)){
                surveyRepository.save(request)

                ResponseEntity.ok()
                        .body(ApiResponse(true, "설문지 변경 완료!"))
            } else {
                throw ResourceNotFoundException("설문지(${request.id})를 찾을 수 없습니다.")
            }

    /**
     * 질문 추가
     * @param userPrincipal 로그인한 유저
     * @param surveyId 질문을 추가할 설문지 ID
     */
    @PostMapping("{surveyId}/question")
    @PreAuthorize("hasRole('USER')")
    fun createQuestion(
            @CurrentUser
            userPrincipal: UserPrincipal,

            @PathVariable
            surveyId: Long,

            @RequestParam(value="index", required = false, defaultValue = "0")
            index: Int
    )= surveyRepository.findById(surveyId).map { survey ->
        if(index<0)
            throw NumberFormatException("인덱스($index)가 0보다 작습니다")

        if(survey.user?.id!=userPrincipal.id)
            throw PermissionDeniedOwnershipException("해당 설문지의 소유자가 아닙니다")

        val questionSize=questionRepository.countBySurvey(survey)
        if(questionSize+1<index)
            throw BadRequestException("삽입 인덱스($index)값이 질문 리스트 크기($questionSize)보다 큽니다")

        val questionNo=if(index>0) {
            questionRepository.updateIndex(index)
            index
        } else {
            questionSize+1
        }

        questionRepository.create {
            this.no=questionNo
            this.survey=survey
        }
    }.orElseThrow {
        ResourceNotFoundException("설문지($surveyId)를 찾을 수 없습니다.")
    }

    /**
     * 질문 수정
     */
    @PreAuthorize("hasRole('USER')")
    @PutMapping("{surveyId}/question")
    fun modifyQuestion(
            @CurrentUser
            userPrincipal: UserPrincipal,

            @PathVariable
            surveyId: Long,

            @Valid
            @RequestBody
            request: Question
    )= questionRepository.findById(request.id).map { question ->
        if (question.survey?.user?.id != userPrincipal.id)
            throw PermissionDeniedOwnershipException("해당 설문지의 소유자가 아닙니다")

        if(question.survey?.id!=surveyId)
            throw ResourceNotFoundException("요청한 설문지($surveyId)를 찾을 수 없습니다")

        questionRepository.update(question){
            this.no=request.no
            this.content=request.content
            this.type=request.type

            if(type== QuestionType.Objective && objectiveItems.size==0){
                objectiveItems.add(ObjectiveItem(1, "항목 1", this))
                objectiveItems.add(ObjectiveItem(2, "항목 2", this))
            }
        }

        ResponseEntity.ok()
                .body(ApiResponse(true, "질문 변경 완료!"))
    }.orElseThrow {
        throw ResourceNotFoundException("요청한 질문(${request.id})을 찾을 수 없습니다")
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("{surveyId}/question/{questionId}")
    fun createQuestionItem(
            @CurrentUser
            userPrincipal: UserPrincipal,

            @PathVariable
            surveyId: Long,

            @PathVariable
            questionId: Long
    )=questionRepository.findById(questionId).map { question ->
        if (question.survey?.user?.id != userPrincipal.id)
            throw PermissionDeniedOwnershipException("해당 설문지의 소유자가 아닙니다")

        if(question.survey?.id!=surveyId)
            throw ResourceNotFoundException("요청한 설문지($surveyId)를 찾을 수 없습니다")

        objectiveItemRepository.create {
            no=objectiveItemRepository.countByQuestion(question)+1
            name="항목 $no"
        }
    }.orElseThrow {
        throw ResourceNotFoundException("요청한 질문($questionId)을 찾을 수 없습니다")
    }
}
