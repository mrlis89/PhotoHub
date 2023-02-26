package com.arnava.photohub.data.repository

import com.arnava.photohub.utils.auth.AppAuth
import com.arnava.photohub.data.local.TokenStorage
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.EndSessionRequest
import net.openid.appauth.TokenRequest
import javax.inject.Inject

class AuthRepository @Inject constructor(private val localRepository: LocalRepository) {

    fun corruptAccessToken() {
        TokenStorage.accessToken = ""
        localRepository.saveTokenToSharedPref("")
    }

    fun getAuthRequest(): AuthorizationRequest {
        return AppAuth.getAuthRequest()
    }

    fun getEndSessionRequest(): EndSessionRequest {
        return AppAuth.getEndSessionRequest()
    }

    suspend fun performTokenRequest(
        authService: AuthorizationService,
        tokenRequest: TokenRequest,
    ) {
        val tokens = AppAuth.performTokenRequestSuspend(authService, tokenRequest)
        //обмен кода на токен произошел успешно, сохраняем токены и завершаем авторизацию
        TokenStorage.accessToken = tokens.accessToken
        TokenStorage.refreshToken = tokens.refreshToken
        localRepository.saveTokenToSharedPref(tokens.accessToken)
    }
}