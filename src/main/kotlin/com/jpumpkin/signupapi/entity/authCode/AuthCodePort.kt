package com.jpumpkin.signupapi.entity.authCode

import com.jpumpkin.signupapi.domain.AuthCode

interface AuthCodePort {
    fun save(authCode: AuthCode)
    fun findByMobileNumberAndCodeAndPurposeAndIsEnabledTrue(
        mobileNumber: String,
        code: Int,
        purpose: AuthCodeJpaEntity.Purpose
    ): AuthCode
}