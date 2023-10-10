package yourssu.yourssuAssigmnet.domain.user.entity

import yourssu.yourssuAssigmnet.domain.common.BaseTimeEntity
import yourssu.yourssuAssigmnet.domain.user.constant.Role
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
    var username: String,

    @Column(length = 250)
    var refreshToken: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    var role: Role
) : BaseTimeEntity() {

    fun encodePassword(password: String?) {
        password?.let {
            this.password = it
        }
    }

    fun updateRole(role: Role?) {
        role?.let {
            this.role = it
        }
    }
}