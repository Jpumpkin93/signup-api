package com.jpumpkin.signupapi.entity.user

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<UserJpaEntity, Long> {
    fun existsByMobileNumber(mobileNumber: String): Boolean
    fun existsByEmail(email: String): Boolean
    fun findByMobileNumber(mobileNumber: String): UserJpaEntity?
    fun findByEmail(email: String): UserJpaEntity?
}