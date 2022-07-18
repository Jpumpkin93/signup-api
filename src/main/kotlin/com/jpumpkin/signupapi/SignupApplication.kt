package com.jpumpkin.signupapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SignupApplication

fun main(args: Array<String>) {
    runApplication<SignupApplication>(*args)
}
