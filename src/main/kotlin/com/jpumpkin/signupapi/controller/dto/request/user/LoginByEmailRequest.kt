package com.jpumpkin.signupapi.controller.dto.request.user

import javax.validation.constraints.NotBlank

data class LoginByEmailRequest(
    @field:NotBlank
    val email: String,
    @field:NotBlank
    val password: String
) {
}