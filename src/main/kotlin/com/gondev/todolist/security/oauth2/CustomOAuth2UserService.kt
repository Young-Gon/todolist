package com.gondev.todolist.security.oauth2

import com.gondev.todolist.exception.OAuth2AuthenticationProcessingException
import com.gondev.todolist.model.User
import com.gondev.todolist.repository.UserRepository
import com.gondev.todolist.repository.create
import com.gondev.todolist.repository.update
import com.gondev.todolist.security.UserPrincipal
import com.gondev.todolist.security.oauth2.user.*
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils

@Service
class CustomOAuth2UserService(
        private val userRepository: UserRepository
) : DefaultOAuth2UserService() {

    @Throws(OAuth2AuthenticationException::class)
    override fun loadUser(oAuth2UserRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User = super.loadUser(oAuth2UserRequest)

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User)
        } catch (ex: AuthenticationException) {
            throw ex
        } catch (ex: Exception) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw InternalAuthenticationServiceException(ex.message, ex.cause)
        }
    }

    private fun processOAuth2User(oAuth2UserRequest: OAuth2UserRequest, oAuth2User: OAuth2User): OAuth2User {
        val oAuth2UserInfo = getOAuth2UserInfo(oAuth2UserRequest.clientRegistration.registrationId, oAuth2User.attributes)
        if (StringUtils.isEmpty(oAuth2UserInfo.email)) {
            throw OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider")
        }

        val userOptional = userRepository.findByEmail(oAuth2UserInfo.email)
        val user=userOptional.map { user ->
            if (user.provider != AuthProvider.valueOf(oAuth2UserRequest.clientRegistration.registrationId)) {
                throw OAuth2AuthenticationProcessingException("""Looks like you're signed up with
                        | ${user.provider} account. Please use your ${user.provider}
                        | account to login.""")
            }
            updateExistingUser(user, oAuth2UserInfo)
        }.orElseGet {
            registerNewUser(oAuth2UserRequest, oAuth2UserInfo)
        }

        if(oAuth2UserRequest.clientRegistration.registrationId.equals(AuthProvider.naver.toString(),false))
            return UserPrincipal(user, oAuth2User.attributes["response"] as MutableMap<String, Any>)

        return UserPrincipal(user, oAuth2User.attributes)
    }

    private fun getOAuth2UserInfo(registrationId: String, attributes: MutableMap<String, Any>) =
            when(registrationId.toLowerCase()){
        AuthProvider.google.toString() -> GoogleOAuth2UserInfo(attributes)
        AuthProvider.facebook.toString() -> FacebookOAuth2UserInfo(attributes)
        AuthProvider.naver.toString() -> NaverOAuth2UserInfo(attributes["response"] as MutableMap<String, Any>)
        AuthProvider.kakao.toString() -> KakaoOAuth2UserInfo(attributes)
        else -> throw OAuth2AuthenticationProcessingException("Sorry! Login with $registrationId is not supported yet.")
    }

    private fun registerNewUser(oAuth2UserRequest: OAuth2UserRequest, oAuth2UserInfo: OAuth2UserInfo) = userRepository.create {
        provider = AuthProvider.valueOf(oAuth2UserRequest.clientRegistration.registrationId)
        providerId = oAuth2UserInfo.id
        name = oAuth2UserInfo.name
        email = oAuth2UserInfo.email
        imageUrl = oAuth2UserInfo.imageUrl
    }

    private fun updateExistingUser(existingUser: User, oAuth2UserInfo: OAuth2UserInfo) = userRepository.update(existingUser) {
        name = oAuth2UserInfo.name
        imageUrl = oAuth2UserInfo.imageUrl
    }
}
