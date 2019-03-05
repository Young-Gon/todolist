package com.gondev.todolist.repository

import com.gondev.todolist.model.DBFile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DBFileRepository : JpaRepository<DBFile, String>
