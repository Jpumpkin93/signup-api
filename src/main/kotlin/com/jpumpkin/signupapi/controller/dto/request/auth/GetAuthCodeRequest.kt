package com.jpumpkin.signupapi.controller.dto.request.auth

data class GetAuthCodeRequest(
    val mobileNumber: String,
    val purpose: AuthPurpose
) {
}