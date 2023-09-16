package yourssu.yourssuAssigmnet.domain.article.entity

import yourssu.yourssuAssigmnet.domain.user.entity.User
import yourssu.yourssuAssigmnet.domain.common.BaseTimeEntity
import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*

@Entity
data class Article(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val articleId: Long? = 0L,

    @Column(nullable = false)
    var content: String,

    @Column(nullable = false)
    var title: String,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    var user: User? = null
) : BaseTimeEntity() {

    fun updateTitle(title: String?) {
        title?.let { this.title = it }
    }

    fun updateContent(content: String?) {
        content?.let { this.content = it }
    }
}
