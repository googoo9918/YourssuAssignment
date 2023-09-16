package yourssu.yourssuAssigmnet.domain.common.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

open class BaseUserDto {
    @Email(message = "이메일 형식으로 입력해주세요")
    @NotBlank(message = "이메일을 입력해주세요")
    var email: String = ""

    @NotBlank(message = "비밀번호를 입력해주세요")
    var password: String = ""
}

