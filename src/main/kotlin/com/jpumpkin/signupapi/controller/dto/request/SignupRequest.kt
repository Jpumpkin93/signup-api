package com.jpumpkin.signupapi.controller.dto.request

import com.jpumpkin.signupapi.domain.User
import org.springframework.security.crypto.password.PasswordEncoder
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class SignupRequest(
    @field:Email
    val email: String,
    @field:NotBlank
    val nickName: String,
    @field:NotBlank
    val password: String,
    @field:NotBlank
    val name: String,
    @field:NotBlank
    val mobileNumber: String
) {

    fun toUser(passwordEncoder: PasswordEncoder, formattedMobileNumber: String) = User(
        email = email,
        nickName = nickName,
        password = passwordEncoder.encode(password),
        name = name,
        mobileNumber = formattedMobileNumber
    )
}