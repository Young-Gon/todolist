package com.gondev.todolist.security.oauth2.user

class GithubOAuth2UserInfo(attributes: MutableMap<String, Any>) : OAuth2UserInfo(attributes) {

    override val id: String
        get() = (attributes["id"] as Int).toString()

    override val name: String
        get() = attributes["name"] as String

    override val email: String
        get() = attributes["email"] as String

    override val imageUrl: String
        get() = attributes["avatar_url"] as String
}
