package com.gondev.todolist.security.oauth2.user

import com.gondev.todolist.exception.OAuth2AuthenticationProcessingException
import com.gondev.todolist.model.AuthProvider

fun getOAuth2UserInfo(registrationId: String, attributes: MutableMap<String, Any>): OAuth2UserInfo {
    return if (registrationId.equals(AuthProvider.google.toString(), ignoreCase = true)) {
        GoogleOAuth2UserInfo(attributes)
    } else if (registrationId.equals(AuthProvider.facebook.toString(), ignoreCase = true)) {
        FacebookOAuth2UserInfo(attributes)
    } else if (registrationId.equals(AuthProvider.github.toString(), ignoreCase = true)) {
        GithubOAuth2UserInfo(attributes)
    } else {
        throw OAuth2AuthenticationProcessingException("Sorry! Login with $registrationId is not supported yet.")
    }
}
