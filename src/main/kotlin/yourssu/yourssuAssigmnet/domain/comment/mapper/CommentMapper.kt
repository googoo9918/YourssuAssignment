package yourssu.yourssuAssigmnet.domain.comment.mapper

import yourssu.yourssuAssigmnet.domain.comment.dto.CommentDto
import yourssu.yourssuAssigmnet.domain.comment.entity.Comment
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface CommentMapper {

    fun commentInputDtoToArticle(commentInputDto: CommentDto.Input): Comment

    fun commentToCommentResponse(comment: Comment, email: String): CommentDto.Response
}
