package com.jpumpkin.signupapi.exception

import com.jpumpkin.signupapi.common.ApiCode
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(Exception::class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    fun unknownException(e: Exception) =
        ResponseEntity(
            ExceptionResponse(ApiCode.INTERNAL_SERVER_ERROR),
            HttpStatus.INTERNAL_SERVER_ERROR
        )

    @ExceptionHandler(ApiException::class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    fun apiException(e: ApiException) =
        ResponseEntity(
            ExceptionResponse(e.code, e.message),
            HttpStatus.BAD_REQUEST
        )
}