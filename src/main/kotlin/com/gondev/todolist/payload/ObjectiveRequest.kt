package com.gondev.todolist.payload

import com.gondev.todolist.model.Objective
import com.gondev.todolist.model.Survey

class ObjectiveRequest(
        id:Long,
        no:Int,
        content:String
): QuestionRequest(id,no,content) {

    val items: MutableList<ObjectiveItemRequest> = mutableListOf()

    override fun toQuestion(survey: Survey) = Objective(
            id = id,
            no = no,
            content = content,
            survey = survey
    ).apply {
        items=this@ObjectiveRequest.items.mapIndexed { index, request ->
            if(request.no==0)
                request.no=index

            request.toObjectiveItem(this)
        }.toMutableList()
    }
}