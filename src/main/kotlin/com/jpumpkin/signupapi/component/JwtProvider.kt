package com.jpumpkin.signupapi.component

import com.jpumpkin.signupapi.common.ApiCode
import com.jpumpkin.signupapi.exception.ApiException
import io.jsonwebtoken.ExpiredJwtException
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

    fun getUserIdByResolveToken(token: String): Long =
        try {
            Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token.replace("Bearer ", ""))
                .body
                .subject
                .toLong()
        } catch (e: ExpiredJwtException) {
            throw ApiException(ApiCode.TOKEN_EXPIRED)
        }


    companion object {
        const val ACCESS_TOKEN_VALID_TIME = 1000L * 60 * 60 * 3
    }
}