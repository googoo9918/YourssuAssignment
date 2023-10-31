package yourssu.yourssuAssigmnet.domain.comment.dto

import yourssu.yourssuAssigmnet.domain.common.dto.BaseUserDto
import javax.validation.constraints.NotBlank

class CommentDto(
   val content: String
) {
    data class Input(
        @field:NotBlank(message = "내용을 입력해주세요")
        val content: String
    )

    data class Response(
        val commentId: Long,
        val email: String,
        val content: String
    )
}
