package com.jpumpkin.signupapi.service

import com.jpumpkin.signupapi.controller.dto.request.auth.AuthPurpose
import com.jpumpkin.signupapi.controller.dto.request.auth.GetAuthCodeRequest
import com.jpumpkin.signupapi.controller.dto.request.auth.VerifyAuthCodeRequest
import com.jpumpkin.signupapi.domain.AuthCode
import com.jpumpkin.signupapi.entity.authCode.AuthCodeJpaEntity
import com.jpumpkin.signupapi.entity.authCode.AuthCodePort
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
internal class AuthServiceTest {

    @Autowired
    lateinit var authService: AuthService

    @Autowired
    lateinit var authCodePort: AuthCodePort

    @Test
    fun `인증 코드를 발급한다`() {
        val request = GetAuthCodeRequest(
            mobileNumber = "01012345678",
            purpose = AuthPurpose.SIGN_UP
        )

        val result = authService.getAuthCode(request)

        assertThat(result).isNotNull
    }

    @Test
    fun `인증 코드를 검증한다`() {
        authCodePort.save(
            AuthCode(
                mobileNumber = "01012345678",
                code = 1234,
                purpose = AuthCodeJpaEntity.Purpose.SIGN_UP,
                isEnabled = true
            )
        )

        val request = VerifyAuthCodeRequest(
            mobileNumber = "01012345678",
            code = 1234,
            purpose = AuthPurpose.SIGN_UP,
        )

        val result = authService.verifyAuthCode(request)

        assertThat(result).startsWith("eyJhbGciOiJIUzI1NiJ9")
    }
}