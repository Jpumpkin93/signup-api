package com.jpumpkin.signupapi.exception

import com.jpumpkin.signupapi.common.ApiCode
import mu.KLogging
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class ControllerAdvice {

    companion object: KLogging()

    @ExceptionHandler(Exception::class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    fun unknownException(e: Exception): ResponseEntity<ExceptionResponse> {
        logger.error("알 수 없는 오류 발생 message: ${e.message}, class: ${e.javaClass.name}", e)
        return ResponseEntity(
            ExceptionResponse(ApiCode.INTERNAL_SERVER_ERROR),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    @ExceptionHandler(ApiException::class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    fun apiException(e: ApiException) =
        ResponseEntity(
            ExceptionResponse(e.code, e.message),
            HttpStatus.BAD_REQUEST
        )

    @ExceptionHandler(EmptyResultDataAccessException::class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    fun emptyResultDataAccessException(e: EmptyResultDataAccessException) =
        ResponseEntity(
            ExceptionResponse(ApiCode.BAD_REQUEST),
            HttpStatus.BAD_REQUEST
        )
}