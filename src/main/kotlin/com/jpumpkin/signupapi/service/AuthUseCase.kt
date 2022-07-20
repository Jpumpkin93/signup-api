package com.jpumpkin.signupapi.service

import com.jpumpkin.signupapi.controller.dto.request.auth.GetAuthCodeRequest
import com.jpumpkin.signupapi.controller.dto.request.auth.VerifyAuthCodeRequest

interface AuthUseCase {
    fun getAuthCode(request: GetAuthCodeRequest): Int
    fun verifyAuthCode(request: VerifyAuthCodeRequest): String
}