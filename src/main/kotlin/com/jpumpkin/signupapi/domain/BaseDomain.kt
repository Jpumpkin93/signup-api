package com.jpumpkin.signupapi.domain

import java.time.LocalDateTime

open class BaseDomain(
    val id: Long? = null,
    val version: Int? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
)