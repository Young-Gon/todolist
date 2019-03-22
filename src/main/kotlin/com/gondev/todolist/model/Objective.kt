package com.gondev.todolist.model

import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import java.util.*
import javax.persistence.*

@Entity
@DiscriminatorValue("objective")
class Objective(
        survey: Survey,
        @OneToMany(mappedBy = "objective", cascade = [CascadeType.ALL], fetch = FetchType.EAGER, orphanRemoval = true)
        @Fetch(FetchMode.SELECT)
        var items: MutableList<ObjectiveItem> = ArrayList()
):Question(survey = survey)