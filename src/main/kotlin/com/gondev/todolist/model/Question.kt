package com.gondev.todolist.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.gondev.todolist.model.audit.BaseAudit
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import javax.persistence.*

@Entity
@Table(uniqueConstraints = [UniqueConstraint(name = "UK_QUESTION_NO", columnNames = ["survey_id", "no"])])
data class Question(
        var no:Int=0,

        var content:String?=null,

        @Enumerated(EnumType.STRING)
        var type:QuestionType = QuestionType.Subjective,

        @JsonIgnore
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name="survey_id", nullable = false)
        var survey: Survey?=null,

        @OneToMany(mappedBy = "question", cascade = [CascadeType.ALL], fetch = FetchType.EAGER, orphanRemoval = true)
        @Fetch(FetchMode.SELECT)
        var objectiveItems: MutableList<ObjectiveItem> = mutableListOf()
):BaseAudit()

enum class QuestionType {
        Subjective,
        Objective,
        LevelSelect,
        MultiChoice,
}
