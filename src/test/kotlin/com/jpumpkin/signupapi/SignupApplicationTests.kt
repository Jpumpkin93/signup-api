package com.jpumpkin.signupapi

import org.junit.jupiter.api.Test
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [SignupApplication::class])
class SignupApplicationTests {

    @Test
    fun contextLoads() {
    }

}
