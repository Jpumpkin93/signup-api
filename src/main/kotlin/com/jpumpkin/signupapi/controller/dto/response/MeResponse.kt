package com.jpumpkin.signupapi.controller.dto.response

import com.jpumpkin.signupapi.domain.User

data class MeResponse(
    val email: String,
    val nickname: String,
    val name: String,
    val mobileNumber: String
) {
    companion object {
        fun from(user: User) =
            MeResponse(
                email = user.email,
                nickname = user.nickName,
                name = user.name,
                mobileNumber = user.mobileNumber
            )
    }
}