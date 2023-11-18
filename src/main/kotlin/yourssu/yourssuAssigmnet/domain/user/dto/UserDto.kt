package yourssu.yourssuAssigmnet.domain.user.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import lombok.AllArgsConstructor
import lombok.Getter
import org.springframework.format.annotation.DateTimeFormat
import yourssu.yourssuAssigmnet.domain.common.dto.BaseUserDto
import yourssu.yourssuAssigmnet.domain.user.constant.Role
import java.time.LocalDate
import java.time.LocalDateTime
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

    @ApiModel(description = "사용자 검색 조건 DTO")
    data class SearchCriteria(
        @ApiModelProperty(value = "사용자 이름", example = "username")
        var username: String? = null,

        @ApiModelProperty(value = "이메일", example = "user@example.com")
        var email: String? = null,

        @ApiModelProperty(value = "생성 시작 날짜", example = "2023-01-01")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        var createdAtStart: LocalDate? = null,

        @ApiModelProperty(value = "생성 종료 날짜", example = "2023-01-31")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        var createdAtEnd: LocalDate? = null,

        @ApiModelProperty(value = "수정 시작 날짜", example = "2023-01-01")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        var updatedAtStart: LocalDate? = null,

        @ApiModelProperty(value = "수정 종료 날짜", example = "2023-01-31")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        var updatedAtEnd: LocalDate? = null
    )

    @ApiModel(description = "사용자 응답 DTO")
    data class ShowResponseDto(
        @ApiModelProperty(value = "ID")
        val id: Long?,

        @ApiModelProperty(value = "이메일")
        val email: String,

        @ApiModelProperty(value = "사용자 이름")
        val username: String,

        @ApiModelProperty(value = "사용자 권한")
        val role: String,

        @ApiModelProperty(value = "생성 날짜")
        val createdAt: LocalDateTime?,

        @ApiModelProperty(value = "수정 날짜")
        val updatedAt: LocalDateTime?
    )
}
