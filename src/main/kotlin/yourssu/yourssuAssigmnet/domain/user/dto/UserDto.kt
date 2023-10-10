package yourssu.yourssuAssigmnet.domain.user.dto

import lombok.AllArgsConstructor
import lombok.Getter
import yourssu.yourssuAssigmnet.domain.common.dto.BaseUserDto
import yourssu.yourssuAssigmnet.domain.user.constant.Role
import yourssu.yourssuAssigmnet.domain.user.entity.User
import yourssu.yourssuAssigmnet.global.jwt.dto.JwtTokenDto
import javax.validation.constraints.NotBlank

class UserDto {

    data class Post(
        @field:NotBlank(message = "이름을 입력해주세요")
        val username: String,
        val role: Role
    ) : BaseUserDto()

    data class Response(
        val email: String,
        val username: String,
        val role: Role
    )
    data class LoginResponse(
        var email: String,
        var username: String,
        var role: Role,
        var accessToken: String,
        var refreshToken: String
    ) {
        companion object {
            fun of(jwtTokenDto: JwtTokenDto, user: User): LoginResponse {
                return LoginResponse(
                    email = user.email,
                    username = user.username,
                    role = user.role,
                    accessToken = jwtTokenDto.getAccessTokenWithGrantType(),
                    refreshToken = jwtTokenDto.getRefreshTokenWithGrantType()
                )
            }
        }
    }
}
