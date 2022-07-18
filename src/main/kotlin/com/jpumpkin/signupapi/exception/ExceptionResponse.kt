package com.jpumpkin.signupapi.exception

import com.jpumpkin.signupapi.common.ApiCode

class ExceptionResponse(
    val code: Int,
    val message: String
) {
    constructor(apiCode: ApiCode): this(apiCode.code, apiCode.message)
}