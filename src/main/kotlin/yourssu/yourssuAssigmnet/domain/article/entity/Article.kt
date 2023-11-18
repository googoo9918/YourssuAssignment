package yourssu.yourssuAssigmnet.domain.article.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import yourssu.yourssuAssigmnet.domain.common.BaseTimeEntity
import yourssu.yourssuAssigmnet.domain.user.entity.User
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

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
