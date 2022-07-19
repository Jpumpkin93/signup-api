package com.jpumpkin.signupapi.service

import com.jpumpkin.signupapi.common.ApiCode
import com.jpumpkin.signupapi.component.JwtProvider
import com.jpumpkin.signupapi.controller.dto.request.LoginByEmailRequest
import com.jpumpkin.signupapi.controller.dto.request.LoginByMobileNumberRequest
import com.jpumpkin.signupapi.controller.dto.request.SignupRequest
import com.jpumpkin.signupapi.controller.dto.response.MeResponse
import com.jpumpkin.signupapi.domain.User
import com.jpumpkin.signupapi.entity.user.UserPort
import com.jpumpkin.signupapi.exception.ApiException
import com.jpumpkin.signupapi.util.MobileNumberUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class UserService(
    val userPort: UserPort,
    val passwordEncoder: PasswordEncoder,
    val jwtProvider: JwtProvider
) : UserUseCase {

    @Transactional
    override fun signup(request: SignupRequest) {
        val formattedMobileNumber = MobileNumberUtil.formatMobileNumber(request.mobileNumber)
        verifyDuplicateMobileNumber(formattedMobileNumber)
        verifyDuplicateEmail(request.email)
        userPort.save(request.toUser(passwordEncoder, formattedMobileNumber))
    }

    private fun verifyDuplicateMobileNumber(mobileNumber: String) {
        if (userPort.existsByMobileNumber(mobileNumber)) throw ApiException(ApiCode.DUPLICATE_MOBILE_NUMBER)
    }

    private fun verifyDuplicateEmail(email: String) {
        if (userPort.existsByEmail(email)) throw ApiException(ApiCode.DUPLICATE_EMAIL)
    }

    override fun loginByMobileNumber(request: LoginByMobileNumberRequest): String {
        val user = userPort.findByMobileNumber(MobileNumberUtil.formatMobileNumber(request.mobileNumber))
            ?: throw ApiException(ApiCode.NOT_EXIST_USER)

        user
            .also { verifyPassword(request.password, it) }
            .run { return jwtProvider.createAccessToken(user.id!!) }
    }

    private fun verifyPassword(requestedPassword: String, user: User) {
        if (passwordEncoder.matches(requestedPassword, user.password).not()) throw ApiException(ApiCode.INVALID_PASSWORD)
    }

    override fun loginByEmail(request: LoginByEmailRequest): String {
        val user = userPort.findByEmail(request.email) ?: throw ApiException(ApiCode.NOT_EXIST_USER)

        user
            .also { verifyPassword(request.password, it) }
            .run { return jwtProvider.createAccessToken(user.id!!) }
    }

    override fun me(token: String): MeResponse =
        MeResponse.from(
            userPort.findById(
                jwtProvider.getUserIdByResolveToken(token)
            )
        )
}