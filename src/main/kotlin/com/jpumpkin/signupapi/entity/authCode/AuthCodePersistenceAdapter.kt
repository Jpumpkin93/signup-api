package com.jpumpkin.signupapi.entity.authCode

import com.jpumpkin.signupapi.domain.AuthCode
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Component
import java.time.LocalDateTime


@Component
class AuthCodePersistenceAdapter(
    val authCodeRepository: AuthCodeRepository,
    val modelMapper: ModelMapper
) : AuthCodePort {
    override fun save(authCode: AuthCode) {
        authCodeRepository.save(
            modelMapper.map(authCode, AuthCodeJpaEntity::class.java)
        )
    }

    override fun findByMobileNumberAndCodeAndPurposeAndIsEnabledTrue(
        mobileNumber: String,
        code: Int,
        purpose: AuthCodeJpaEntity.Purpose
    ): AuthCode = modelMapper.map(
            authCodeRepository.findByMobileNumberAndCodeAndPurposeAndIsEnabledTrue(mobileNumber, code, purpose),
            AuthCode::class.java
        )
}