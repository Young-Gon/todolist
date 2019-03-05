package com.gondev.todolist.controller

import com.gondev.todolist.exception.FileStorageException
import com.gondev.todolist.exception.MyFileNotFoundException
import com.gondev.todolist.payload.UploadFileResponse
import com.gondev.todolist.repository.DBFileRepository
import com.gondev.todolist.repository.create
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.io.IOException

@RestController
class FileController(
        private val dbFileRepository: DBFileRepository
) {
    @PostMapping("/uploadFile")
    fun uploadFile(@RequestParam("file") file: MultipartFile): ResponseEntity<UploadFileResponse> {
        val dbFile = storeFile(file)

        val location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(dbFile.id!!)
                .buildAndExpand(dbFile.id!!).toUri()

        return ResponseEntity.created(location)
                .body(UploadFileResponse(dbFile.fileName ,location.toString(),file.contentType, file.size))
    }

    @PostMapping("/uploadMultipleFiles")
    fun uploadMultipleFiles(@RequestParam("files") files: Array<MultipartFile>) =
            files.map {
                uploadFile(it).body
            }

    @GetMapping("/downloadFile/{fileId}")
    fun downloadFile(@PathVariable fileId: String): ResponseEntity<Resource> {
        // Load file from database
        dbFileRepository.findById(fileId)
                .orElseThrow { MyFileNotFoundException("File not found with id $fileId") }.apply {
                    return ResponseEntity.ok()
                            .contentType(fileType?.let { MediaType.parseMediaType(it) }?: MediaType.ALL)//MediaType.parseMediaType(dbFile.fileType))
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"$fileName\"")
                            .body(ByteArrayResource(data!!))
                }
    }

    private fun storeFile(file: MultipartFile)=try {
        dbFileRepository.create {
            fileName = file.originalFilename?.let { StringUtils.cleanPath(it) }
            if (fileName==null || fileName!!.contains("..")) {
                throw FileStorageException("Sorry! Filename contains invalid path sequence $fileName")
            }
            fileType=file.contentType
            data=file.bytes
        }
    }catch (ex: IOException) {
        throw FileStorageException("Could not store file ${file.originalFilename}. Please try again!", ex)
    }

}