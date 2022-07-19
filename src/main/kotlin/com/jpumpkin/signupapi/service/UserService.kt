package com.jpumpkin.signupapi.service

import com.jpumpkin.signupapi.common.ApiCode
import com.jpumpkin.signupapi.controller.dto.request.SignupRequest
import com.jpumpkin.signupapi.entity.user.UserPort
import com.jpumpkin.signupapi.exception.ApiException
import com.jpumpkin.signupapi.util.MobileNumberUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class UserService(
    val userPort: UserPort,
    val passwordEncoder: PasswordEncoder
): UserUseCase {

    @Transactional
    override fun signup(request: SignupRequest) {
        val formattedMobileNumber = MobileNumberUtil.formatMobileNumber(request.mobileNumber)
        verifyDuplicateMobileNumber(formattedMobileNumber)
        verifyDuplicateEmail(request.email)
        userPort.save(request.toUser(passwordEncoder, formattedMobileNumber))
    }

    private fun verifyDuplicateMobileNumber(mobileNumber: String) {
        if(userPort.existsByMobileNumber(mobileNumber)) throw ApiException(ApiCode.DUPLICATE_MOBILE_NUMBER)
    }
    private fun verifyDuplicateEmail(email: String) {
        if (userPort.existsByEmail(email)) throw ApiException(ApiCode.DUPLICATE_EMAIL)
    }
}