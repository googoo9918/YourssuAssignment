package yourssu.yourssuAssigmnet.domain.user.dto

import lombok.AllArgsConstructor
import lombok.Getter
import yourssu.yourssuAssigmnet.domain.common.dto.BaseUserDto
import javax.validation.constraints.NotBlank

class UserDto {

    @Getter
    data class Post(
        @field:NotBlank(message = "이름을 입력해주세요")
        val username: String
    ) : BaseUserDto()

    @Getter
    @AllArgsConstructor
    data class Response(
        val email: String,
        val username: String
    )
}
