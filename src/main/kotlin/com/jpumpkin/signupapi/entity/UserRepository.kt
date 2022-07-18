package com.jpumpkin.signupapi.entity

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<UserJpaEntity, Long> {
}