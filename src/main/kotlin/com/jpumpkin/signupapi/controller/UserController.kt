package com.jpumpkin.signupapi.controller

import com.jpumpkin.signupapi.common.ApiCode
import com.jpumpkin.signupapi.controller.dto.request.user.SignupRequest
import com.jpumpkin.signupapi.common.ApiResponse
import com.jpumpkin.signupapi.controller.dto.request.user.LoginByEmailRequest
import com.jpumpkin.signupapi.controller.dto.request.user.LoginByMobileNumberRequest
import com.jpumpkin.signupapi.controller.dto.request.user.UpdatePasswordRequest
import com.jpumpkin.signupapi.service.UserUseCase
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController


@RestController
class UserController(
    val userUseCase: UserUseCase
) {

    @PostMapping("/signup")
    fun signup(
        @RequestHeader("referrer-token") token: String,
        @RequestBody request: SignupRequest
    ) = ok(ApiResponse(ApiCode.SUCCESS, userUseCase.signup(token, request)))

    @PostMapping("/login/mobile-number")
    fun loginByMobileNumber(
        @RequestBody request: LoginByMobileNumberRequest
    ) = ok(ApiResponse(ApiCode.SUCCESS, userUseCase.loginByMobileNumber(request)))

    @PostMapping("/login/email")
    fun loginByEmail(
        @RequestBody request: LoginByEmailRequest
    ) = ok(ApiResponse(ApiCode.SUCCESS, userUseCase.loginByEmail(request)))

    @GetMapping("/me")
    fun me(
        @RequestHeader("Authorization") token: String
    ) = ok(ApiResponse(ApiCode.SUCCESS, userUseCase.me(token)))

    @PutMapping("/password")
    fun updatePassword(
        @RequestHeader("referrer-token") token: String,
        @RequestBody request: UpdatePasswordRequest
    ) = ok(ApiResponse(ApiCode.SUCCESS, userUseCase.updatePassword(token, request)))
}