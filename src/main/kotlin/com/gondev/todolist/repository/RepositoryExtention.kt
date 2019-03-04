package com.gondev.todolist.repository

import org.springframework.data.repository.CrudRepository
import kotlin.reflect.full.createInstance

inline fun <reified T : Any, R> CrudRepository<T, R>.update(entity: T, block: T.() -> Unit) =
        this.save(entity.apply(block))

inline fun <reified T : Any, R> CrudRepository<T, R>.create(block: T.() -> Unit) =
        this.save(T::class.createInstance().apply(block))
