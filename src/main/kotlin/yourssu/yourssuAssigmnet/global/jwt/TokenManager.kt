package yourssu.yourssuAssigmnet.global.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import yourssu.yourssuAssigmnet.domain.user.constant.Role
import yourssu.yourssuAssigmnet.global.jwt.dto.JwtTokenDto
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import yourssu.yourssuAssigmnet.global.error.ErrorCode
import yourssu.yourssuAssigmnet.global.error.exception.AuthenticationException
import yourssu.yourssuAssigmnet.global.jwt.constant.GrantType
import yourssu.yourssuAssigmnet.global.jwt.constant.TokenType
import java.nio.charset.StandardCharsets
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

    fun validateToken(token: String) {
        // 토큰 유효성 확인 메서드
        try {
            Jwts.parser().setSigningKey(tokenSecret.toByteArray(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
        } catch (e: ExpiredJwtException) {
            throw AuthenticationException(ErrorCode.TOKEN_EXPIRED)
        } catch (e: Exception) {
            throw AuthenticationException(ErrorCode.NOT_VALID_TOKEN)
        }
    }

    fun getTokenClaims(token: String): Claims {
        // 토큰 정보를 서버에서 사용할 때, payload에 있는 claim 정보들을 가져오는 메서드
        val claims: Claims
        try {
            claims = Jwts.parser().setSigningKey(tokenSecret.toByteArray(StandardCharsets.UTF_8))
                .parseClaimsJws(token).body
        } catch (e: Exception) {
            throw AuthenticationException(ErrorCode.NOT_VALID_TOKEN)
        }
        return claims
    }

}