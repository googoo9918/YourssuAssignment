package yourssu.yourssuAssigmnet.domain.user.dto

import lombok.AllArgsConstructor
import lombok.Getter
import yourssu.yourssuAssigmnet.domain.common.dto.BaseUserDto
import yourssu.yourssuAssigmnet.domain.user.constant.Role
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
        val email: String,
        val username: String,
        val role: Role,
        val accessToken: String,
        val refreshToken: String
    )
}
