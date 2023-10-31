package yourssu.yourssuAssigmnet.domain.user.entity

import yourssu.yourssuAssigmnet.domain.common.BaseTimeEntity
import javax.persistence.*

@Entity
@Table(name = "`user`")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Long? = 0L,

    @Column(nullable = false, unique = true)
    var email: String,

    @Column(nullable = false)
    var password: String,

    @Column(nullable = false)
    var username: String
) : BaseTimeEntity() {

    fun encoderPassword(password: String?) {
        password?.let {
            this.password = it
        }
    }
}