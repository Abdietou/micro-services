package org.devabdi.secservice.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.Date
import javax.crypto.SecretKey


object JWTUtil {
    private val SECRET: String = System.getenv("MY_SECRET")
    private val EXPIRE_ACCESS_TOKEN: String = System.getenv("EXPIRE_ACCESS_TOKEN")
    private val EXPIRE_REFRESH_TOKEN: String = System.getenv("EXPIRE_REFRESH_TOKEN")
    private val key: SecretKey = getSecretKey()
    private val logger: Logger = LoggerFactory.getLogger(JWTUtil::class.java)

    const val AUTH_HEADER: String = "Authorization"
    const val PREFIX: String = "Bearer "

    fun generateToken(userId: Long, email: String, requestUrl: String, roles: List<String>): String {
        val now = Date()
        val userIdString = userId.toString()
        val expiration = Date(now.time + getExpireAccessToken())

        return Jwts.builder().subject(userIdString).claim("user_id", userId).claim("roles", roles).claim("email", email)
            .expiration(expiration)
            .issuer(requestUrl)
            .signWith(key).compact()
    }

    fun generateRefreshToken(userId: Long, email: String, requestUrl: String, roles: List<String>): String {
        val now = Date()
        val userIdString = userId.toString()
        val expiration = Date(now.time + getExpireRefreshToken())

        return Jwts.builder().subject(userIdString)
            .expiration(expiration)
            .issuer(requestUrl)
            .signWith(key).compact()
    }

    fun getUserId(token: String): Long? {
        return getClaims(token)?.get("user_id") as? Long
    }

    fun getEmail(token: String): String? {
        return getClaims(token)?.get("email") as? String
    }

    fun getRoles(token: String): List<String> {
        val roles = getClaims(token)?.get("roles")
        return if (roles is List<*>) {
            roles.filterIsInstance<String>()
        } else {
            emptyList()
        }
    }

    fun isTokenValid(token: String): Boolean {
        val claims = getClaims(token) ?: throw IllegalArgumentException("Expiration not found in claims")
        val expirationDate = claims.expiration
        val now = Date(System.currentTimeMillis())
        return now.before(expirationDate);
    }

    private fun getExpireAccessToken(): Long {
        return EXPIRE_ACCESS_TOKEN.toLongOrNull() ?: 3_600L
    }

    private fun getExpireRefreshToken(): Long {
        return EXPIRE_REFRESH_TOKEN.toLongOrNull() ?: 86_400L
    }

    private fun getSecretKey(): SecretKey {
        val keyBytes = Decoders.BASE64.decode(SECRET)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    private fun getClaims(token: String): Claims? {
        return try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token).payload
        } catch (e: IllegalArgumentException) {
            logger.error("Invalid token : ${e.message}")
            null
        } catch (e: JwtException) {
            logger.error("Error during token validation : ${e.message}")
            null
        }
    }
}