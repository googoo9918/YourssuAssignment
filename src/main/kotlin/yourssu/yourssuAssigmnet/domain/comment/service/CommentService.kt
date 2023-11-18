package yourssu.yourssuAssigmnet.domain.comment.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yourssu.yourssuAssigmnet.domain.article.service.ArticleService
import yourssu.yourssuAssigmnet.domain.comment.entity.Comment
import yourssu.yourssuAssigmnet.domain.comment.repository.CommentRepository
import yourssu.yourssuAssigmnet.domain.user.service.UserService
import yourssu.yourssuAssigmnet.global.error.ErrorCode
import yourssu.yourssuAssigmnet.global.error.exception.BusinessException
import yourssu.yourssuAssigmnet.global.error.exception.EntityNotFoundException

@Service
@Transactional
class CommentService(
    private val commentRepository: CommentRepository,
    private val userService: UserService,
    private val articleService: ArticleService
) {
    // 댓글 생성
    fun createComment(articleId: Long, email: String, comment: Comment): Comment {
//        val validateUser = userService.validateRegisterUser(email, password)
        val article = articleService.findVerifiedArticeByArticeId(articleId)
        comment.user = userService.findUserByEmail(email)
        comment.article = article
        return commentRepository.save(comment)
    }

    // 댓글 수정
    fun updateComment(articleId: Long, commentId: Long, email: String, comment: Comment): Comment {
        val preComment = findVerifiedCommentByCommentId(commentId)

        // 게시글에 속하는 댓글인지 확인
        validateCommentBelongsToArticle(preComment, articleId)

        // 등록된 회원인지 확인, 작성자와 일치하는지 확인
//        val validatedUser = userService.validateUserAndAuthor(email, password, preComment.user)
        userService.validateAuthor(userService.findUserByEmail(email), preComment.user)
        comment.content?.let { preComment.updateContent(it) }

        return commentRepository.save(preComment)
    }

    // 댓글 삭제
    fun deleteComment(articleId: Long, commentId: Long, email: String) {
        val comment = findVerifiedCommentByCommentId(commentId)

        // 게시글에 속하는 댓글인지 확인
        validateCommentBelongsToArticle(comment, articleId)

        // 등록된 회원인지 확인, 작성자와 일치하는지 확인
//        val validatedUser = userService.validateUserAndAuthor(email, password, comment.user)
        userService.validateAuthor(userService.findUserByEmail(email), comment.user)
        commentRepository.deleteById(commentId)
    }

    // 댓글 조회
    private fun findVerifiedCommentByCommentId(commentId: Long): Comment {
        return commentRepository.findById(commentId).orElseThrow {
            EntityNotFoundException(ErrorCode.COMMENT_NOT_EXISTS)
        }
    }

    // 글에 달린 댓글이 맞는지 유효성 체크
    private fun validateCommentBelongsToArticle(comment: Comment, articleId: Long) {
        if (comment.article?.articleId != articleId) {
            throw BusinessException(ErrorCode.UNRELATED_COMMENT_TO_ARTICLE)
        }
    }
}
