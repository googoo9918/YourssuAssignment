package yourssu.yourssuAssigmnet.domain.comment.controller

import lombok.RequiredArgsConstructor
import yourssu.yourssuAssigmnet.domain.comment.dto.CommentDto
import yourssu.yourssuAssigmnet.domain.comment.entity.Comment
import yourssu.yourssuAssigmnet.domain.comment.mapper.CommentMapper
import yourssu.yourssuAssigmnet.domain.comment.service.CommentService
import yourssu.yourssuAssigmnet.domain.common.dto.BaseUserDto
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.Positive

@RestController
@RequestMapping("/api/comments")
@Validated
class CommentController(
    private val commentService: CommentService,
    private val commentMapper: CommentMapper
) {

    @PostMapping("/{articleId}")
    fun postComment(
        @PathVariable @Positive articleId: Long,
        @Valid @RequestBody commentPostDto: CommentDto.Input
    ): ResponseEntity<CommentDto.Response> {
        val comment = commentService.createComment(
            articleId, commentPostDto.email, commentPostDto.password,
            commentMapper.commentInputDtoToArticle(commentPostDto)
        )
        val response = commentMapper.commentToCommentResponse(comment, commentPostDto.email)
        return ResponseEntity.ok(response)
    }

    @PatchMapping("/{articleId}/{commentId}")
    fun updateComment(
        @PathVariable @Positive articleId: Long,
        @PathVariable @Positive commentId: Long,
        @Valid @RequestBody commentPatchDto: CommentDto.Input
    ): ResponseEntity<CommentDto.Response> {
        val comment = commentService.updateComment(
            articleId, commentId, commentPatchDto.email, commentPatchDto.password,
            commentMapper.commentInputDtoToArticle(commentPatchDto)
        )
        val response = commentMapper.commentToCommentResponse(comment, commentPatchDto.email)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{articleId}/{commentId}")
    fun deleteComment(
        @PathVariable @Positive articleId: Long,
        @PathVariable @Positive commentId: Long,
        @Valid @RequestBody commentDeleteDto: BaseUserDto
    ): ResponseEntity<Void> {
        commentService.deleteComment(articleId, commentId, commentDeleteDto.email, commentDeleteDto.password)
        return ResponseEntity.ok().build()
    }
}
