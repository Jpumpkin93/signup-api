package com.jpumpkin.signupapi.extension

import com.jpumpkin.signupapi.common.ApiCode
import com.jpumpkin.signupapi.exception.ApiException
import org.springframework.data.repository.CrudRepository

fun <T> CrudRepository<T, Long>.findByIdOrThrow(id: Long): T {
    return findById(id).orElseThrow { ApiException(ApiCode.NOT_FOUND) }
}