package yourssu.yourssuAssigmnet.global.jwt.service

import org.springframework.stereotype.Service
import yourssu.yourssuAssigmnet.domain.user.constant.Role
import yourssu.yourssuAssigmnet.domain.user.service.UserService
import yourssu.yourssuAssigmnet.global.error.ErrorCode
import yourssu.yourssuAssigmnet.global.error.exception.AuthenticationException
import yourssu.yourssuAssigmnet.global.jwt.JwtUtil
import yourssu.yourssuAssigmnet.global.jwt.dto.TokenResponseDto

@Service
class JwtService(
    private val jwtUtil: JwtUtil,
    private val userService: UserService
) {
    fun createAccessAndRefresh(token: String?): TokenResponseDto {
        if (token != null && jwtUtil.validateToken(token)) {
            val email = jwtUtil.getEmailFromToken(token)
            val role = jwtUtil.getRoleFromToken(token)
            val newRefreshToken = jwtUtil.generateRefreshToken(email)
            userService.findUserByEmail(email)?.let { userService.updateRefreshToken(it, newRefreshToken) }
            val newAccessToken = jwtUtil.generateAccessToken(email, Role.valueOf(role))
            return TokenResponseDto("Bearer $newAccessToken", "Bearer $newRefreshToken")
        }
        throw AuthenticationException(ErrorCode.NOT_VALID_TOKEN)
    }
}
