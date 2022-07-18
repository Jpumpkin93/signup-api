package com.jpumpkin.signupapi.domain

import com.jpumpkin.signupapi.common.Domain


@Domain
class User(
    val email: String,
    val nickName: String,
    val password: String,
    val name: String,
    val mobileNumber: String
): BaseDomain() {
}