package com.jpumpkin.signupapi.service

import com.jpumpkin.signupapi.common.ApiCode
import com.jpumpkin.signupapi.component.JwtProvider
import com.jpumpkin.signupapi.controller.dto.request.auth.GetAuthCodeRequest
import com.jpumpkin.signupapi.controller.dto.request.auth.VerifyAuthCodeRequest
import com.jpumpkin.signupapi.domain.AuthCode
import com.jpumpkin.signupapi.entity.authCode.AuthCodeJpaEntity
import com.jpumpkin.signupapi.entity.authCode.AuthCodePort
import com.jpumpkin.signupapi.exception.ApiException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime.now


@Service
class AuthService(
    val authCodePort: AuthCodePort,
    val jwtProvider: JwtProvider
) : AuthUseCase {

    @Transactional
    override fun getAuthCode(request: GetAuthCodeRequest): Int {
        AuthCode(
            mobileNumber = request.mobileNumber,
            code = generateRandomNumber(),
            purpose = AuthCodeJpaEntity.Purpose.valueOf(request.purpose.name),
            isEnabled = true
        )
            .run {
                authCodePort.save(this)
                return this.code
            }
    }

    @Transactional
    override fun verifyAuthCode(request: VerifyAuthCodeRequest): String {
        authCodePort.findByMobileNumberAndCodeAndPurposeAndIsEnabledTrue(
            request.mobileNumber,
            request.code,
            AuthCodeJpaEntity.Purpose.valueOf(request.purpose.name)
        )
            .also { verifyAuthCodeValidTime(it) }
            .apply { used() }
            .run {
                authCodePort.save(this)
                return jwtProvider.createReferrerToken(this)
            }
    }

    private fun verifyAuthCodeValidTime(authCode: AuthCode) {
        if (authCode.createdAt!!.plusMinutes(AUTH_CODE_VALID_TIME) < now() ) throw ApiException(ApiCode.EXPIRED_AUTH_CODE)
    }

    private fun generateRandomNumber() = (1000..9999).random()

    companion object {
        const val AUTH_CODE_VALID_TIME = 5L
    }
}