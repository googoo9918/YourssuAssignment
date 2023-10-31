package yourssu.yourssuAssigmnet.domain.article.dto

import yourssu.yourssuAssigmnet.domain.common.dto.BaseUserDto
import javax.validation.constraints.NotBlank

class ArticleDto(
    val input: Input,
    val response: Response
) {
    data class Input(
        @field:NotBlank(message = "제목을 입력해주세요")
        val title: String,

        @field:NotBlank(message = "내용을 입력해주세요")
        val content: String
    ) : BaseUserDto()

    data class Response(
        val articleId: Long,
        val email: String,
        val title: String,
        val content: String
    )
}
