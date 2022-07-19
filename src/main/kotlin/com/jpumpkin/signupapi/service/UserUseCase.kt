package com.jpumpkin.signupapi.service

import com.jpumpkin.signupapi.controller.dto.request.LoginByEmailRequest
import com.jpumpkin.signupapi.controller.dto.request.LoginByMobileNumberRequest
import com.jpumpkin.signupapi.controller.dto.request.SignupRequest

interface UserUseCase {
    fun signup(request: SignupRequest)
    fun loginByMobileNumber(request: LoginByMobileNumberRequest): String
    fun loginByEmail(request: LoginByEmailRequest): String

}