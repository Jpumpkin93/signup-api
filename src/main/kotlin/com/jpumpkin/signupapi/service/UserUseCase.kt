package com.jpumpkin.signupapi.service

import com.jpumpkin.signupapi.controller.dto.request.SignupRequest

interface UserUseCase {
    fun signup(request: SignupRequest)

}