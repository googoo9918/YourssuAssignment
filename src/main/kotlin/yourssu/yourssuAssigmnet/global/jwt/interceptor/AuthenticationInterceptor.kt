package yourssu.yourssuAssigmnet.global.jwt.interceptor

import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import yourssu.yourssuAssigmnet.global.error.ErrorCode
import yourssu.yourssuAssigmnet.global.error.exception.AuthenticationException
import yourssu.yourssuAssigmnet.global.jwt.TokenManager
import yourssu.yourssuAssigmnet.global.jwt.constant.TokenType
import yourssu.yourssuAssigmnet.global.jwt.util.AuthorizationHeaderUtils
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthenticationInterceptor(
    private val tokenManager: TokenManager
) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {

        // 1. Authorization Header 검증
        val authorizationHeader = request.getHeader("Authorization")
        AuthorizationHeaderUtils.validateAuthorization(authorizationHeader)

        // 2. 토큰 검증
        val token = authorizationHeader.split(" ")[1]
        tokenManager.validateToken(token)

        // 3. 토큰 타입 (accessToken이 올 때만 인증 작동하게 구현)
        val tokenClaims = tokenManager.getTokenClaims(token)
        val tokenType = tokenClaims.subject
        if (!TokenType.isAccessToken(tokenType)) {
            throw AuthenticationException(ErrorCode.NOT_ACCESS_TOKEN_TYPE)
        }

        return true
    }
}
