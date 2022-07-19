package com.jpumpkin.signupapi.entity.user

import com.jpumpkin.signupapi.domain.User

interface UserPort {
    fun save(user: User): UserJpaEntity
    fun existsByMobileNumber(mobileNumber: String): Boolean
    fun existsByEmail(email: String): Boolean
}