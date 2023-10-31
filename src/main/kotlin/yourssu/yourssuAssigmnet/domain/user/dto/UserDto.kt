package yourssu.yourssuAssigmnet.domain.user.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import lombok.AllArgsConstructor
import lombok.Getter
import yourssu.yourssuAssigmnet.domain.common.dto.BaseUserDto
import yourssu.yourssuAssigmnet.domain.user.constant.Role
import javax.validation.constraints.NotBlank

@ApiModel(description = "사용자 DTO")
class UserDto {

    @ApiModel(description = "사용자 등록 DTO")
    data class Post(
        @field:NotBlank(message = "이름을 입력해주세요")
        @ApiModelProperty(value = "사용자 이름", required = true, example = "username")
        val username: String,

        @ApiModelProperty(value = "사용자 권한", example = "USER")
        val role: Role
    ) : BaseUserDto()

    @ApiModel(description = "사용자 응답 DTO")
    data class Response(
        @ApiModelProperty(value = "이메일")
        val email: String,
        @ApiModelProperty(value = "사용자 이름")
        val username: String,
        @ApiModelProperty(value = "사용자 권한")
        val role: Role
    )

    @ApiModel(description = "로그인 응답 DTO")
    data class LoginResponse(
        @ApiModelProperty(value = "이메일")
        val email: String,
        @ApiModelProperty(value = "사용자 이름")
        val username: String,
        @ApiModelProperty(value = "사용자 권한")
        val role: Role,
        @ApiModelProperty(value = "액세스 토큰")
        val accessToken: String,
        @ApiModelProperty(value = "리프레시 토큰")
        val refreshToken: String
    )
}
