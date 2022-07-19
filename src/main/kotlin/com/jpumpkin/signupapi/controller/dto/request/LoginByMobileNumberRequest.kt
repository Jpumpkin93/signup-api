package com.jpumpkin.signupapi.controller.dto.request

import javax.validation.constraints.NotBlank

data class LoginByMobileNumberRequest(
    @field:NotBlank
    val mobileNumber: String,
    @field:NotBlank
    val password: String
) {
}