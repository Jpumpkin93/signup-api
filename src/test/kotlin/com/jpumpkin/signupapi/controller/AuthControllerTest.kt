package com.jpumpkin.signupapi.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.jpumpkin.signupapi.controller.dto.request.auth.AuthPurpose
import com.jpumpkin.signupapi.controller.dto.request.auth.GetAuthCodeRequest
import com.jpumpkin.signupapi.controller.dto.request.auth.VerifyAuthCodeRequest
import com.jpumpkin.signupapi.service.AuthUseCase
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put


@WebMvcTest(AuthController::class)
internal class AuthControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var authUseCase: AuthUseCase

    @Test
    fun `인증 코드 발급을 요청한다`() {
        val request = GetAuthCodeRequest(
            mobileNumber = "01012345678",
            purpose = AuthPurpose.SIGN_UP
        )

        mockMvc.post("/auth-code") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(request)
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    fun `인증 코드 검증을 요청한다`() {
        val request = VerifyAuthCodeRequest(
            mobileNumber = "01012345678",
            purpose = AuthPurpose.SIGN_UP,
            code = 1234
        )

        mockMvc.put("/auth-code") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(request)
        }.andExpect {
            status { isOk() }
        }
    }
}