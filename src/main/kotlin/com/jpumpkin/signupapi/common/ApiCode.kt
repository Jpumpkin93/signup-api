package com.jpumpkin.signupapi.common

enum class ApiCode(
    val code: Int,
    val message: String
) {
    SUCCESS(200, "success"),

    NOT_EXIST_USER(1000, "not exist user"),
    INVALID_PASSWORD(1001, "invalid password"),
    TOKEN_EXPIRED(2000, "token expired"),

    DUPLICATE_MOBILE_NUMBER(3000, "duplicate mobile number"),
    DUPLICATE_EMAIL(3001, "duplicate email"),

    NOT_FOUND(8000, "not found"),
    INTERNAL_SERVER_ERROR(9999, "internal server error")
}