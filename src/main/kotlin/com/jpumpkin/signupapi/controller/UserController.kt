package com.jpumpkin.signupapi.controller

import com.jpumpkin.signupapi.common.ApiCode
import com.jpumpkin.signupapi.controller.dto.request.SignupRequest
import com.jpumpkin.signupapi.common.ApiResponse
import com.jpumpkin.signupapi.controller.dto.request.LoginByEmailRequest
import com.jpumpkin.signupapi.controller.dto.request.LoginByMobileNumberRequest
import com.jpumpkin.signupapi.service.UserUseCase
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class UserController(
    val userUseCase: UserUseCase
) {

    @PostMapping("/signup")
    fun signup(
        @RequestBody request: SignupRequest
    ) = ok(ApiResponse(ApiCode.SUCCESS, userUseCase.signup(request)))

    @PostMapping("/login/mobile-number")
    fun loginByMobileNumber(
        @RequestBody request: LoginByMobileNumberRequest
    ) = ok(ApiResponse(ApiCode.SUCCESS, userUseCase.loginByMobileNumber(request)))

    @PostMapping("/login/email")
    fun loginByEmail(
        @RequestBody request: LoginByEmailRequest
    ) = ok(ApiResponse(ApiCode.SUCCESS, userUseCase.loginByEmail(request)))

    @GetMapping("/me")
    fun me() = ok()

    @PutMapping("/password")
    fun updatePassword() = ok()
}