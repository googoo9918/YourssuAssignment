package yourssu.yourssuAssigmnet.domain.comment.repository

import yourssu.yourssuAssigmnet.domain.comment.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long>
