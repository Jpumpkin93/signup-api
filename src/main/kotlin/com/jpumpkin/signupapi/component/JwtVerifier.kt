package com.jpumpkin.signupapi.component

import com.jpumpkin.signupapi.common.ApiCode
import com.jpumpkin.signupapi.exception.ApiException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component


@Component
class JwtVerifier(
    @Value("\${jwt.secretKey}")
    val secretKey: String
) {

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
            throw ApiException(ApiCode.EXPIRED_TOKEN)
        }

    fun getReferrerTokenClaims(token: String): Claims =
        try {
            Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .body
        } catch (e: ExpiredJwtException) {
            throw ApiException(ApiCode.EXPIRED_TOKEN)
        }
}