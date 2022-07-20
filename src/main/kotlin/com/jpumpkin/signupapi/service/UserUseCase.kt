package com.jpumpkin.signupapi.service

import com.jpumpkin.signupapi.controller.dto.request.user.LoginByEmailRequest
import com.jpumpkin.signupapi.controller.dto.request.user.LoginByMobileNumberRequest
import com.jpumpkin.signupapi.controller.dto.request.user.SignupRequest
import com.jpumpkin.signupapi.controller.dto.request.user.UpdatePasswordRequest
import com.jpumpkin.signupapi.controller.dto.response.user.MeResponse

interface UserUseCase {
    fun signup(token: String, request: SignupRequest)
    fun loginByMobileNumber(request: LoginByMobileNumberRequest): String
    fun loginByEmail(request: LoginByEmailRequest): String
    fun me(token: String): MeResponse
    fun updatePassword(token: String, request: UpdatePasswordRequest)
}