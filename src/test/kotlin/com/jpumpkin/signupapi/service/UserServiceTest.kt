package com.jpumpkin.signupapi.service

import com.jpumpkin.signupapi.component.JwtProvider
import com.jpumpkin.signupapi.controller.dto.request.user.LoginByEmailRequest
import com.jpumpkin.signupapi.controller.dto.request.user.LoginByMobileNumberRequest
import com.jpumpkin.signupapi.controller.dto.request.user.SignupRequest
import com.jpumpkin.signupapi.controller.dto.request.user.UpdatePasswordRequest
import com.jpumpkin.signupapi.domain.AuthCode
import com.jpumpkin.signupapi.domain.User
import com.jpumpkin.signupapi.entity.authCode.AuthCodeJpaEntity
import com.jpumpkin.signupapi.entity.user.UserPort
import com.jpumpkin.signupapi.exception.ApiException
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.transaction.annotation.Transactional


@SpringBootTest
internal class UserServiceTest {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var jwtProvider: JwtProvider

    @Autowired
    lateinit var userPort: UserPort

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Test
    @Transactional
    fun `회원 가입을 한다`() {
        val token = jwtProvider.createReferrerToken(
            AuthCode(
                mobileNumber = "01012345678",
                code = 1234,
                purpose = AuthCodeJpaEntity.Purpose.SIGN_UP,
                isEnabled = true
            )
        )
        val request = SignupRequest(
            email = "jpumpkin93@gmail.com",
            nickName = "jpumpkin",
            password = "jpumpkin1234!!",
            name = "박정호"
        )

        userService.signup(token, request)
    }

    @Test
    fun `회원 가입 시 referrerToken의 목적이 다르면 exception이 발생한다`() {
        val token = jwtProvider.createReferrerToken(
            AuthCode(
                mobileNumber = "01012345678",
                code = 1234,
                purpose = AuthCodeJpaEntity.Purpose.PASSWORD_UPDATE,
                isEnabled = true
            )
        )
        val request = SignupRequest(
            email = "jpumpkin93@gmail.com",
            nickName = "jpumpkin",
            password = "jpumpkin1234!!",
            name = "박정호"
        )

        assertThrows<ApiException> { userService.signup(token, request) }
    }

    @Test
    @Transactional
    fun `이미 가입이 되어있는 핸드폰 번호 회원 가입을 요청하면 exception이 발생한다`() {
        userPort.save(createTestUser())
        val token = jwtProvider.createReferrerToken(
            AuthCode(
                mobileNumber = "01012345678",
                code = 1234,
                purpose = AuthCodeJpaEntity.Purpose.SIGN_UP,
                isEnabled = true
            )
        )
        val request = SignupRequest(
            email = "jpumpkin93@gmail.com",
            nickName = "jpumpkin",
            password = "jpumpkin1234!!",
            name = "박정호"
        )

        assertThrows<ApiException> { userService.signup(token, request) }
    }

    @Test
    @Transactional
    fun `이미 가입이 되어있는 이메일로 회원 가입을 요청하면 exception이 발생한다`() {
        userPort.save(createTestUser())
        val token = jwtProvider.createReferrerToken(
            AuthCode(
                mobileNumber = "01012345678",
                code = 1234,
                purpose = AuthCodeJpaEntity.Purpose.SIGN_UP,
                isEnabled = true
            )
        )
        val request = SignupRequest(
            email = "jpumpkin93@gmail.com",
            nickName = "jpumpkin",
            password = "jpumpkin1234!!",
            name = "박정호"
        )

        assertThrows<ApiException> { userService.signup(token, request) }
    }

    @Test
    @Transactional
    fun `핸드폰 번호로 로그인 한다`() {
        userPort.save(createTestUser())

        val request = LoginByMobileNumberRequest(
            mobileNumber = "01012345678",
            password = "jpumpkin1234!!"
        )

        val result = userService.loginByMobileNumber(request)
        assertThat(result).startsWith("eyJhbGciOiJIUzI1NiJ9")
    }

    @Test
    fun `가입 되지 않은 핸드폰 번호로 로그인 시 exception이 발생한다`() {
        val request = LoginByMobileNumberRequest(
            mobileNumber = "01012345678",
            password = "jpumpkin1234!!"
        )

        assertThrows<ApiException> { userService.loginByMobileNumber(request) }
    }

    @Test
    @Transactional
    fun `핸드폰 번호로 로그인 시 비밀번호가 다르면 exception이 발생한다`() {
        userPort.save(createTestUser())

        val request = LoginByMobileNumberRequest(
            mobileNumber = "01012345678",
            password = "jpumpkin1234"
        )

        assertThrows<ApiException> { userService.loginByMobileNumber(request) }
    }

    @Test
    @Transactional
    fun `이메일로 로그인 한다`() {
        userPort.save(createTestUser())

        val request = LoginByEmailRequest(
            email = "jpumpkin93@gmail.com",
            password = "jpumpkin1234!!"
        )

        val result = userService.loginByEmail(request)

        assertThat(result).startsWith("eyJhbGciOiJIUzI1NiJ9")
    }

    @Test
    fun `가입 되지 않은 이메일로 로그인 시 exception이 발생한다`() {
        val request = LoginByEmailRequest(
            email = "jpumpkin93@gmail.com",
            password = "jpumpkin1234!!"
        )

        assertThrows<ApiException> { userService.loginByEmail(request) }
    }

    @Test
    @Transactional
    fun `이메일로 로그인 시 비밀번호가 다르면 exception이 발생한다`() {
        userPort.save(createTestUser())

        val request = LoginByEmailRequest(
            email = "jpumpkin93@gmail.com",
            password = "jpumpkin1234"
        )

        assertThrows<ApiException> { userService.loginByEmail(request) }
    }

    @Test
    @Transactional
    fun `내 정보를 불러온다`() {
        userPort.save(createTestUser())
        val token = jwtProvider.createAccessToken(1L)

        val result = userService.me(token)

        assertThat(result.email).isEqualTo("jpumpkin93@gmail.com")
        assertThat(result.nickname).isEqualTo("jpumpkin93")
        assertThat(result.name).isEqualTo("박정호")
        assertThat(result.mobileNumber).isEqualTo("01012345678")
    }

    @Test
    @Transactional
    fun `비밀번호를 변경한다`() {
        userPort.save(createTestUser())
        val token = jwtProvider.createReferrerToken(
            AuthCode(
                mobileNumber = "01012345678",
                code = 1234,
                purpose = AuthCodeJpaEntity.Purpose.PASSWORD_UPDATE,
                isEnabled = true
            )
        )
        val request = UpdatePasswordRequest("jpumpkin")

        userService.updatePassword(token, request)

        val user = userPort.findByMobileNumber("01012345678")!!
        assertThat(passwordEncoder.matches("jpumpkin", user.password)).isTrue
    }

    @Test
    fun `비밀번호 변경 시 referrerToken의 목적이 다르면 exception이 발생한다`() {
        val token = jwtProvider.createReferrerToken(
            AuthCode(
                mobileNumber = "01012345678",
                code = 1234,
                purpose = AuthCodeJpaEntity.Purpose.SIGN_UP,
                isEnabled = true
            )
        )
        val request = UpdatePasswordRequest("jpumpkin")

        assertThrows<ApiException> { userService.updatePassword(token, request) }
    }

    @Test
    fun `존재하지 않는 유저의 비밀번호를 변경하려 하면 exception이 발생한다`() {
        val token = jwtProvider.createReferrerToken(
            AuthCode(
                mobileNumber = "01012345678",
                code = 1234,
                purpose = AuthCodeJpaEntity.Purpose.PASSWORD_UPDATE,
                isEnabled = true
            )
        )
        val request = UpdatePasswordRequest("jpumpkin")

        assertThrows<ApiException> { userService.updatePassword(token, request) }
    }


    private fun createTestUser() = User(
        email = "jpumpkin93@gmail.com",
        nickName = "jpumpkin93",
        password = passwordEncoder.encode("jpumpkin1234!!"),
        name = "박정호",
        mobileNumber = "01012345678"
    )
}