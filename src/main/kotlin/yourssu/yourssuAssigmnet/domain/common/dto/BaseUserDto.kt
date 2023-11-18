package yourssu.yourssuAssigmnet.domain.common.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

@ApiModel(description = "기본 사용자 DTO")
open class BaseUserDto {
    @Email(message = "이메일 형식으로 입력해주세요")
    @ApiModelProperty(value = "이메일", required = true, example = "email@urssu.com")
    @NotBlank(message = "이메일을 입력해주세요")
    var email: String = ""

    @ApiModelProperty(value = "비밀번호", required = true, example = "password")
    @NotBlank(message = "비밀번호를 입력해주세요")
    var password: String = ""
}
