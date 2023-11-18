package yourssu.yourssuAssigmnet.domain.comment.repository

import org.springframework.data.jpa.repository.JpaRepository
import yourssu.yourssuAssigmnet.domain.comment.entity.Comment

interface CommentRepository : JpaRepository<Comment, Long>
