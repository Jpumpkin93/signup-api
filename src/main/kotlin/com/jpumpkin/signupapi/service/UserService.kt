package com.jpumpkin.signupapi.service

import com.jpumpkin.signupapi.common.ApiCode
import com.jpumpkin.signupapi.component.JwtProvider
import com.jpumpkin.signupapi.component.JwtVerifier
import com.jpumpkin.signupapi.controller.dto.request.auth.AuthPurpose
import com.jpumpkin.signupapi.controller.dto.request.user.LoginByEmailRequest
import com.jpumpkin.signupapi.controller.dto.request.user.LoginByMobileNumberRequest
import com.jpumpkin.signupapi.controller.dto.request.user.SignupRequest
import com.jpumpkin.signupapi.controller.dto.request.user.UpdatePasswordRequest
import com.jpumpkin.signupapi.controller.dto.response.user.MeResponse
import com.jpumpkin.signupapi.domain.User
import com.jpumpkin.signupapi.entity.user.UserPort
import com.jpumpkin.signupapi.exception.ApiException
import io.jsonwebtoken.Claims
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class UserService(
    val userPort: UserPort,
    val passwordEncoder: PasswordEncoder,
    val jwtProvider: JwtProvider,
    val jwtVerifier: JwtVerifier
) : UserUseCase {

    @Transactional
    override fun signup(token: String, request: SignupRequest) {
        val mobileNumber = jwtVerifier.getReferrerTokenClaims(token)
            .also { verifyReferrerTokenPurpose(it, AuthPurpose.SIGN_UP) }
            .run { getMobileNumber(this) }

        verifyDuplicateMobileNumber(mobileNumber)
        verifyDuplicateEmail(request.email)

        userPort.save(request.toUser(passwordEncoder, mobileNumber))
    }

    private fun verifyReferrerTokenPurpose(claims: Claims, authPurpose: AuthPurpose) {
        val purpose = claims["purpose", String::class.java] ?: throw ApiException(ApiCode.INTERNAL_SERVER_ERROR)
        if (purpose != authPurpose.name) throw ApiException(ApiCode.INVALID_TOKEN_PURPOSE)
    }

    private fun getMobileNumber(claims: Claims): String =
        claims["mobileNumber", String::class.java] ?: throw ApiException(ApiCode.INTERNAL_SERVER_ERROR)

    private fun verifyDuplicateMobileNumber(mobileNumber: String) {
        if (userPort.existsByMobileNumber(mobileNumber)) throw ApiException(ApiCode.DUPLICATE_MOBILE_NUMBER)
    }

    private fun verifyDuplicateEmail(email: String) {
        if (userPort.existsByEmail(email)) throw ApiException(ApiCode.DUPLICATE_EMAIL)
    }

    @Transactional(readOnly = true)
    override fun loginByMobileNumber(request: LoginByMobileNumberRequest): String {
        val user = userPort.findByMobileNumber(request.mobileNumber)
            ?: throw ApiException(ApiCode.NOT_EXIST_USER)

        user
            .also { verifyPassword(request.password, it) }
            .run { return jwtProvider.createAccessToken(user.id!!) }
    }

    private fun verifyPassword(requestedPassword: String, user: User) {
        if (passwordEncoder.matches(requestedPassword, user.password).not()) throw ApiException(ApiCode.INVALID_PASSWORD)
    }

    @Transactional(readOnly = true)
    override fun loginByEmail(request: LoginByEmailRequest): String {
        val user = userPort.findByEmail(request.email) ?: throw ApiException(ApiCode.NOT_EXIST_USER)

        user
            .also { verifyPassword(request.password, it) }
            .run { return jwtProvider.createAccessToken(user.id!!) }
    }

    @Transactional(readOnly = true)
    override fun me(token: String): MeResponse =
        MeResponse.from(
            userPort.findById(
                jwtVerifier.getUserIdByResolveToken(token)
            )
        )

    @Transactional
    override fun updatePassword(token: String, request: UpdatePasswordRequest) {
        val mobileNumber = jwtVerifier.getReferrerTokenClaims(token)
            .also { verifyReferrerTokenPurpose(it, AuthPurpose.PASSWORD_UPDATE) }
            .run { getMobileNumber(this) }

        val user = userPort.findByMobileNumber(mobileNumber) ?: throw ApiException(ApiCode.NOT_EXIST_USER)

        user.apply { updatePassword(passwordEncoder.encode(request.password)) }
            .run { userPort.save(this) }
    }
}