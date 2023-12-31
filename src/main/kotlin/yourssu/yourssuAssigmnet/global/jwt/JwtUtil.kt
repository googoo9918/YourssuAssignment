package yourssu.yourssuAssigmnet.global.jwt

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import yourssu.yourssuAssigmnet.domain.user.constant.Role
import yourssu.yourssuAssigmnet.global.error.ErrorCode
import yourssu.yourssuAssigmnet.global.error.exception.AuthenticationException
import java.nio.charset.StandardCharsets
import java.util.*

@Component
class JwtUtil {

    @Value("\${token.secret}")
    private lateinit var tokenSecret: String

    @Value("\${token.access-token-expiration-time}")
    private lateinit var accessTokenExpirationTime: String

    @Value("\${token.refresh-token-expiration-time}")
    private lateinit var refreshTokenExpirationTime: String

    fun generateAccessToken(email: String, role: Role): String {
        val expirationTime = Date(System.currentTimeMillis() + accessTokenExpirationTime.toLong())

        return Jwts.builder()
            .setSubject(email)
            .claim("role", role.name)
            .setIssuedAt(Date())
            .setExpiration(expirationTime)
            .signWith(SignatureAlgorithm.HS512, tokenSecret.toByteArray(Charsets.UTF_8))
            .compact()
    }

    fun generateRefreshToken(email: String): String {
        val expirationTime = Date(System.currentTimeMillis() + refreshTokenExpirationTime.toLong())

        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(Date())
            .setExpiration(expirationTime)
            .signWith(SignatureAlgorithm.HS512, tokenSecret.toByteArray(Charsets.UTF_8))
            .compact()
    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parser().setSigningKey(tokenSecret.toByteArray(StandardCharsets.UTF_8)).parseClaimsJws(token)
            return true
        } catch (e: JwtException) {
            throw AuthenticationException(ErrorCode.NOT_VALID_TOKEN)
        }
    }

    fun getEmailFromToken(token: String): String {
        return Jwts.parser().setSigningKey(tokenSecret.toByteArray()).parseClaimsJws(token).body.subject
    }

    fun getRoleFromToken(token: String): String {
        return Jwts.parser()
            .setSigningKey(tokenSecret.toByteArray())
            .parseClaimsJws(token)
            .body["role"].toString()
    }
}
