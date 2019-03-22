package com.gondev.todolist.model

import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import javax.persistence.*

@Entity
data class Survey(
        val title: String,

        var description: String?=null,

        @OneToMany(mappedBy = "survey", cascade = [CascadeType.ALL], fetch = FetchType.EAGER, orphanRemoval = true)
        @Fetch(FetchMode.SELECT)
        var questions:MutableList<Question> = arrayListOf(),

        @ManyToOne
        @JoinColumn(foreignKey = ForeignKey(name = "fk_user"))
        var user: User
):BaseEntity() /*{
        fun newQuestion(question: Question) {
                question.survey=this
                questions.add(question)
        }
}*/