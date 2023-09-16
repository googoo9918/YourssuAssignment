package yourssu.yourssuAssigmnet.domain.user.repository


import org.springframework.data.jpa.repository.JpaRepository
import yourssu.yourssuAssigmnet.domain.user.entity.User

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
}
