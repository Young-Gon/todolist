package com.gondev.todolist.security.oauth2.user

abstract class OAuth2UserInfo(var attributes: MutableMap<String, Any>) {

    abstract val id: String

    abstract val name: String

    abstract val email: String

    abstract val imageUrl: String?

}
