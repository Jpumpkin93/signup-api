package com.jpumpkin.signupapi.common

enum class ApiCode(
    val code: Int,
    val message: String
) {
    SUCCESS(200, "success"),

    NOT_EXIST_USER(1000, "not exist user"),
    INVALID_PASSWORD(1001, "invalid password"),

    DUPLICATE_MOBILE_NUMBER(3000, "duplicate mobile number"),
    DUPLICATE_EMAIL(3001, "duplicate email"),

    INTERNAL_SERVER_ERROR(9999, "internal server error")
}