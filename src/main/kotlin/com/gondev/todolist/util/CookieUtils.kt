package com.gondev.todolist.util

import org.springframework.util.SerializationUtils
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

object CookieUtils {

    fun getCookie(request: HttpServletRequest, name: String): Cookie? =
            request.cookies.find { cookie ->
        cookie.name==name
    }

    fun addCookie(response: HttpServletResponse, name: String, value: String, maxAge: Int) =
            response.addCookie(Cookie(name, value).apply {
                path = "/"
                isHttpOnly = true
                this.maxAge = maxAge
            })

    fun deleteCookie(request: HttpServletRequest, response: HttpServletResponse, name: String) =
        request.cookies?.filter { cookie ->
            cookie.name==name
        }?.forEach { cookie: Cookie ->
            cookie.value = ""
            cookie.path = "/"
            cookie.maxAge = 0
            response.addCookie(cookie)
        }

    fun serialize(obj: Any): String =
            Base64.getUrlEncoder().encodeToString(SerializationUtils.serialize(obj))

    fun <T> deserialize(cookie: Cookie, cls: Class<T>): T? =
            cls.cast(SerializationUtils.deserialize(Base64.getUrlDecoder().decode(cookie.value)))
}
