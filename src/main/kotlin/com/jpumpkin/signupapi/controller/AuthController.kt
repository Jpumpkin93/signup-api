package com.jpumpkin.signupapi.controller

import com.jpumpkin.signupapi.common.ApiCode
import com.jpumpkin.signupapi.common.ApiResponse
import com.jpumpkin.signupapi.controller.dto.request.auth.GetAuthCodeRequest
import com.jpumpkin.signupapi.controller.dto.request.auth.VerifyAuthCodeRequest
import com.jpumpkin.signupapi.service.AuthUseCase
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class AuthController(
    val authUseCase: AuthUseCase
) {

    @PostMapping("/auth-code")
    fun getAuthCode(
        @RequestBody request: GetAuthCodeRequest
    ) = ok(ApiResponse(ApiCode.SUCCESS, authUseCase.getAuthCode(request)))

    @PutMapping("/auth-code")
    fun verifyAuthCode(
        @RequestBody request: VerifyAuthCodeRequest
    ) = ok(ApiResponse(ApiCode.SUCCESS, authUseCase.verifyAuthCode(request)))

}