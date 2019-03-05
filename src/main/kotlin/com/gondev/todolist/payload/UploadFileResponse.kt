package com.gondev.todolist.payload


data class UploadFileResponse(
        var fileName: String?,
        var fileDownloadUri: String?,
        var fileType: String?,
        var size: Long=0
)
