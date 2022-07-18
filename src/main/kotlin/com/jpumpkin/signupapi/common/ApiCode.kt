package com.jpumpkin.signupapi.common

enum class ApiCode(
    val code: Int,
    val message: String
) {
    SUCCESS(200, "success"),

    INTERNAL_SERVER_ERROR(9999, "internal server error")
}