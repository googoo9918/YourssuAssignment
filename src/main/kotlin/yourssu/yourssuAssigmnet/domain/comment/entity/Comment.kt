package yourssu.yourssuAssigmnet.domain.comment.entity

import yourssu.yourssuAssigmnet.domain.user.entity.User
import yourssu.yourssuAssigmnet.domain.article.entity.Article
import yourssu.yourssuAssigmnet.domain.common.BaseTimeEntity
import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*

@Entity
data class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val commentId: Long = 0L,

    @Column(nullable = false)
    var content: String,

    @ManyToOne
    @JoinColumn(name = "article_id",nullable = false)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    var article: Article? = null,

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    var user: User? = null
) : BaseTimeEntity() {

    fun updateContent(content: String?) {
        content?.let { this.content = it }
    }
}
