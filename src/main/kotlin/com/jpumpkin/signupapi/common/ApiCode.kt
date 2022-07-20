package com.jpumpkin.signupapi.common

enum class ApiCode(
    val code: Int,
    val message: String
) {
    SUCCESS(200, "success"),
    BAD_REQUEST(400, "bad request"),

    NOT_EXIST_USER(1000, "not exist user"),
    INVALID_PASSWORD(1001, "invalid password"),

    EXPIRED_TOKEN(2000, "token expired"),
    INVALID_TOKEN_PURPOSE(2001, "invalid token purpose"),

    DUPLICATE_MOBILE_NUMBER(3000, "duplicate mobile number"),
    DUPLICATE_EMAIL(3001, "duplicate email"),

    EXPIRED_AUTH_CODE(4000, "expired auth code"),

    NOT_FOUND(8000, "not found"),
    INTERNAL_SERVER_ERROR(9999, "internal server error")
}