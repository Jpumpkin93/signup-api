package com.jpumpkin.signupapi.controller

import com.jpumpkin.signupapi.service.UserUseCase
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class UserController(
    val userUseCase: UserUseCase
) {

    @PostMapping("/signup")
    fun signup() = ok()

    @PostMapping("/login")
    fun login() = ok()

    @GetMapping("/me")
    fun me() = ok()

    @PutMapping("/password")
    fun updatePassword() = ok()
}