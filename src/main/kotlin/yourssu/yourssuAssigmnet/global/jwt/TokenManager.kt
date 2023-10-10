package yourssu.yourssuAssigmnet.global.jwt

import yourssu.yourssuAssigmnet.domain.user.constant.Role
import yourssu.yourssuAssigmnet.global.jwt.dto.JwtTokenDto
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import yourssu.yourssuAssigmnet.global.jwt.constant.GrantType
import yourssu.yourssuAssigmnet.global.jwt.constant.TokenType
import java.util.*

class TokenManager(
    private val accessTokenExpirationTime: String,
    private val refreshTokenExpirationTime: String,
    private val tokenSecret: String
) {

    fun createJwtTokenDto(memberId: Long, role: Role): JwtTokenDto {
        val accessTokenExpireTime = createAccessTokenExpireTime()
        val refreshTokenExpireTime = createRefreshTokenExpireTime()

        val accessToken = createAccessToken(memberId, role, accessTokenExpireTime)
        val refreshToken = createRefreshToken(memberId, refreshTokenExpireTime)
        return JwtTokenDto(
            grantType = GrantType.BEARER.type,
            accessToken = accessToken,
            accessTokenExpireTime = accessTokenExpireTime,
            refreshToken = refreshToken,
            refreshTokenExpireTime = refreshTokenExpireTime
        )
    }

    private fun createAccessTokenExpireTime(): Date {
        return Date(System.currentTimeMillis() + accessTokenExpirationTime.toLong())
    }

    private fun createRefreshTokenExpireTime(): Date {
        return Date(System.currentTimeMillis() + refreshTokenExpirationTime.toLong())
    }

    private fun createAccessToken(memberId: Long, role: Role, expirationTime: Date): String {
        return Jwts.builder()
            .setSubject(TokenType.ACCESS.name)
            .setIssuedAt(Date())
            .setExpiration(expirationTime)
            .claim("memberId", memberId)
            .claim("role", role)
            .signWith(SignatureAlgorithm.HS512, tokenSecret.toByteArray(Charsets.UTF_8))
            .setHeaderParam("typ", "JWT")
            .compact()
    }

    private fun createRefreshToken(memberId: Long, expirationTime: Date): String {
        return Jwts.builder()
            .setSubject(TokenType.REFRESH.name)
            .setIssuedAt(Date())
            .setExpiration(expirationTime)
            .claim("memberId", memberId)
            .signWith(SignatureAlgorithm.HS512, tokenSecret.toByteArray(Charsets.UTF_8))
            .setHeaderParam("typ", "JWT")
            .compact()
    }
}