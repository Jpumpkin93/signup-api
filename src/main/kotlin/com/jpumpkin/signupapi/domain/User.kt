package com.jpumpkin.signupapi.domain

import com.jpumpkin.signupapi.common.Domain


@Domain
class User(
    val email: String,
    val nickName: String,
    var password: String,
    val name: String,
    val mobileNumber: String
): BaseDomain() {

    fun updatePassword(password: String) {
        this.password = password
    }
}