package com.jpumpkin.signupapi.controller.dto.request.auth

data class VerifyAuthCodeRequest(
    val mobileNumber: String,
    val code: Int,
    val purpose: AuthPurpose
)