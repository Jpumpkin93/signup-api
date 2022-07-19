package com.jpumpkin.signupapi.common

data class ApiResponse<T>(
    val code: Int,
    val message: String,
    val data: T? = null
) {
    constructor(apiCode: ApiCode): this(apiCode.code, apiCode.message)
    constructor(apiCode: ApiCode, data: T): this(apiCode.code, apiCode.message, data)
}