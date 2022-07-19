package com.jpumpkin.signupapi.component

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*


@Component
class JwtProvider(
    @Value("\${jwt.secretKey}")
    val secretKey: String
) {

    fun createAccessToken(userId: Long): String =
         Jwts.builder()
            .setSubject(userId.toString())
            .setExpiration(Date(System.currentTimeMillis() + ACCESS_TOKEN_VALID_TIME))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()

    companion object {
        const val ACCESS_TOKEN_VALID_TIME = 1000L * 60 * 60 * 3
    }
}