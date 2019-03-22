package com.gondev.todolist.model

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("subjective")
class Subjective(
        survey: Survey,
        var answer:String?=null
): Question(survey = survey)