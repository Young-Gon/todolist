package com.gondev.todolist.repository

import org.springframework.data.jpa.repository.JpaRepository
import kotlin.reflect.full.createInstance

inline fun <reified T : Any, R> JpaRepository<T, R>.save(entity: T, block: T.() -> Unit) =
        this.save(entity.apply(block))

inline fun <reified T : Any, R> JpaRepository<T, R>.create(block: T.() -> Unit) =
        this.save(T::class.createInstance().apply(block))
