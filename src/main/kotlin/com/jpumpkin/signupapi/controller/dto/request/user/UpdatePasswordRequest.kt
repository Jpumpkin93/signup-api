package com.jpumpkin.signupapi.controller.dto.request.user

import javax.validation.constraints.NotBlank

data class UpdatePasswordRequest(
    @field:NotBlank
    val password: String
) {
}