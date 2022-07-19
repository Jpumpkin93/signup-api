package com.jpumpkin.signupapi.exception

import com.jpumpkin.signupapi.common.ApiCode

class ApiException(
    val code: Int,
    override val message: String
): RuntimeException() {

    constructor(apiCode: ApiCode): this(apiCode.code, apiCode.message)
}