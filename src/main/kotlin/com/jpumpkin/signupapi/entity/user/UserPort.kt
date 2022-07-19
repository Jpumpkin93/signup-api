package com.jpumpkin.signupapi.entity.user

import com.jpumpkin.signupapi.domain.User

interface UserPort {
    fun save(user: User): UserJpaEntity
    fun existsByMobileNumber(mobileNumber: String): Boolean
    fun existsByEmail(email: String): Boolean
    fun findByMobileNumber(mobileNumber: String): User?
    fun findByEmail(email: String): User?
    fun findById(userId: Long): User
}