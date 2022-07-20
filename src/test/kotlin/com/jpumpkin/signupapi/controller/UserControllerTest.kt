package com.jpumpkin.signupapi.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.jpumpkin.signupapi.component.JwtProvider
import com.jpumpkin.signupapi.controller.dto.request.user.LoginByEmailRequest
import com.jpumpkin.signupapi.controller.dto.request.user.LoginByMobileNumberRequest
import com.jpumpkin.signupapi.controller.dto.request.user.SignupRequest
import com.jpumpkin.signupapi.controller.dto.request.user.UpdatePasswordRequest
import com.jpumpkin.signupapi.domain.AuthCode
import com.jpumpkin.signupapi.entity.authCode.AuthCodeJpaEntity
import com.jpumpkin.signupapi.service.UserUseCase
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put


@WebMvcTest(UserController::class)
internal class UserControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var userUseCase: UserUseCase

    private val jwtProvider = JwtProvider("jpumpkin")

    @Test
    fun `회원가입을 요청한다`() {
        val request = SignupRequest(
            email = "jpumpkin93@gmail.com",
            nickName = "jpumpkin",
            password = "jpumpkin1234!!",
            name = "박정호"
        )

        mockMvc.post("/signup") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(request)
            header("referrer-token", createTestReferrerToken())
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    fun `휴대폰 번호로 로그인을 요청한다`() {
        val request = LoginByMobileNumberRequest(
            mobileNumber = "01012345678",
            password = "jpumpkin1234!!"
        )

        mockMvc.post("/login/mobile-number") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(request)
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    fun `이메일로 로그인을 요청한다`() {
        val request = LoginByEmailRequest(
            email = "jpumpkin93@gmail.com",
            password = "jpumpkin1234!!"
        )

        mockMvc.post("/login/email") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(request)
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    fun `내 정보를 요청한다`() {
        mockMvc.get("/me") {
            header("Authorization", createTestAccessToken())
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    fun `비밀번호 변경을 요청한다`() {
        val request = UpdatePasswordRequest(
            password = "jpumpkin1234!!",
        )

        mockMvc.put("/password") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(request)
            header("referrer-token", createTestReferrerToken())
        }.andExpect {
            status { isOk() }
        }
    }

    private fun createTestReferrerToken() =
        jwtProvider.createReferrerToken(
            AuthCode(
                mobileNumber = "",
                code = 1234,
                purpose = AuthCodeJpaEntity.Purpose.SIGN_UP,
                isEnabled = true
            )
        )

    private fun createTestAccessToken() =
        "bearer ${jwtProvider.createAccessToken(1L)}"
}