package com.jpumpkin.signupapi.entity.authCode

import org.springframework.data.jpa.repository.JpaRepository

interface AuthCodeRepository : JpaRepository<AuthCodeJpaEntity, Long> {
    fun findByMobileNumberAndCodeAndPurposeAndIsEnabledTrue(
        mobileNumber: String,
        code: Int,
        purpose: AuthCodeJpaEntity.Purpose
    ): AuthCodeJpaEntity
}