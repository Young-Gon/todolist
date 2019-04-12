package com.gondev.todolist.model

import com.gondev.todolist.model.audit.OwnerShipAudit
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.OneToMany

@Entity
data class Survey(
        var title: String="",

        var description: String?=null,

        @OneToMany(mappedBy = "survey", cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
        @Fetch(FetchMode.SELECT)
        var questions:MutableList<Question> = arrayListOf()
):OwnerShipAudit()
