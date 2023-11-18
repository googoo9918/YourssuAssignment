package yourssu.yourssuAssigmnet.domain.comment.mapper

import org.mapstruct.Mapper
import yourssu.yourssuAssigmnet.domain.comment.dto.CommentDto
import yourssu.yourssuAssigmnet.domain.comment.entity.Comment

@Mapper(componentModel = "spring")
interface CommentMapper {

    fun commentInputDtoToArticle(commentInputDto: CommentDto.Input): Comment

    fun commentToCommentResponse(comment: Comment, email: String): CommentDto.Response
}
