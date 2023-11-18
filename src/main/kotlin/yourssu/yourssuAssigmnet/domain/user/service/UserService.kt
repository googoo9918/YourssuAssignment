package yourssu.yourssuAssigmnet.domain.user.service

import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import yourssu.yourssuAssigmnet.domain.user.entity.User
import yourssu.yourssuAssigmnet.domain.user.repository.UserRepository
import yourssu.yourssuAssigmnet.global.error.ErrorCode
import yourssu.yourssuAssigmnet.global.error.exception.BusinessException
import yourssu.yourssuAssigmnet.global.error.exception.EntityNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yourssu.yourssuAssigmnet.domain.common.dto.BaseUserDto
import yourssu.yourssuAssigmnet.domain.user.constant.Role
import yourssu.yourssuAssigmnet.domain.user.dto.UserDto
import yourssu.yourssuAssigmnet.domain.user.entity.QUser
import yourssu.yourssuAssigmnet.global.jwt.JwtUtil
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val jwtUtil: JwtUtil,
    private val jpaQueryFactory: JPAQueryFactory
) {

    fun createUser(user: User): User {
        validateDuplicateUser(user)
        user.encodePassword(passwordEncoder.encode(user.password))
        return userRepository.save(user)
    }

    fun login(userLoginDto: BaseUserDto) : UserDto.LoginResponse {
        val validateUser = validateRegisterUser(userLoginDto.email, userLoginDto.password)
        val accessToken = jwtUtil.generateAccessToken(validateUser.email, validateUser.role)
        val refreshToken = jwtUtil.generateRefreshToken(validateUser.email)

        validateUser.updateRefreshToken(refreshToken)
        userRepository.save(validateUser)

        return UserDto.LoginResponse(
            validateUser.email,
            validateUser.username,
            validateUser.role,
            accessToken,
            refreshToken
        )
    }
    fun findUsersWithFilters(
        username: String?,
        email: String?,
        createdAtStart: LocalDate?,
        createdAtEnd: LocalDate?,
        updatedAtStart: LocalDate?,
        updatedAtEnd: LocalDate?
    ): List<User> {
        val user = QUser.user
        val builder = BooleanBuilder(user.role.eq(Role.USER))

        username?.let { builder.and(user.username.eq(it)) }
        email?.let { builder.and(user.email.eq(it)) }
        createdAtStart?.let { builder.and(user.createdAt.goe(LocalDateTime.of(it, LocalTime.MIN))) }
        createdAtEnd?.let { builder.and(user.createdAt.loe(LocalDateTime.of(it, LocalTime.MAX))) }
        updatedAtStart?.let { builder.and(user.updatedAt.goe(LocalDateTime.of(it, LocalTime.MIN))) }
        updatedAtEnd?.let { builder.and(user.updatedAt.loe(LocalDateTime.of(it, LocalTime.MAX))) }

        return jpaQueryFactory
            .selectFrom(user)
            .where(builder)
            .orderBy(user.userId.desc())
            .fetch()
    }
    fun updateRefreshToken(user: User, newRefreshToken: String){
        user?.updateRefreshToken(newRefreshToken)
        userRepository.save(user)
    }

    private fun validateDuplicateUser(user: User) {
        findUserByEmail(user.email)?.let {
            throw BusinessException(ErrorCode.ALREADY_REGISTERED_MEMBER)
        }
    }

    fun validateRegisterUser(email: String, password: String): User {
        val user = findUserByEmail(email) ?: throw BusinessException(ErrorCode.MEMBER_NOT_EXISTS)
        if (!passwordEncoder.matches(password, user.password)) {
            throw BusinessException(ErrorCode.PASSWORD_MISMATCH)
        }
        return user
    }

    fun validateAuthor(loginUser: User?, authorUser: User?) {
        if (loginUser != authorUser) {
            throw BusinessException(ErrorCode.AUTHOR_MISMATCH)
        }
    }

    fun validateUserAndAuthor(email: String, password: String, author: User?): User {
        val validatedUser = validateRegisterUser(email, password)
        validateAuthor(validatedUser, author)
        return validatedUser
    }

    fun findUserByEmail(email: String): User? = userRepository.findByEmail(email)

    fun deleteUser(email: String) {
//        val user = validateRegisterUser(email, password)
        val user = findUserByEmail(email)
        userRepository.deleteById(user?.userId!!)
    }
}
