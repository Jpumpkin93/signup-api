package com.jpumpkin.signupapi.domain

import com.jpumpkin.signupapi.common.Domain
import com.jpumpkin.signupapi.entity.authCode.AuthCodeJpaEntity


@Domain
class AuthCode(
    val mobileNumber: String,
    val code: Int,
    val purpose: AuthCodeJpaEntity.Purpose,
    var isEnabled: Boolean
): BaseDomain() {

    fun used() {
        isEnabled = false
    }
}