package com.gondev.todolist.model

import org.hibernate.annotations.GenericGenerator

import javax.persistence.*

@Entity
@Table(name = "files")
data class DBFile (

    var fileName: String? = null,

    var fileType: String? = null,

    @Lob
    var data: ByteArray? = null,

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    var id: String? = null
)
