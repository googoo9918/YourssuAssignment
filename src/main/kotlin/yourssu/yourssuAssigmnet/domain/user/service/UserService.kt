package yourssu.yourssuAssigmnet.domain.user.service

import yourssu.yourssuAssigmnet.domain.user.entity.User
import yourssu.yourssuAssigmnet.domain.user.repository.UserRepository
import yourssu.yourssuAssigmnet.global.error.ErrorCode
import yourssu.yourssuAssigmnet.global.error.exception.BusinessException
import yourssu.yourssuAssigmnet.global.error.exception.EntityNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yourssu.yourssuAssigmnet.domain.common.dto.BaseUserDto
import yourssu.yourssuAssigmnet.domain.user.dto.UserDto
import yourssu.yourssuAssigmnet.global.jwt.JwtUtil

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val jwtUtil: JwtUtil
) {

    fun createUser(user: User): User {
        validateDuplicateUser(user)
        user.encodePassword(passwordEncoder.encode(user.password))
        return userRepository.save(user)
    }

    fun login(userLoginDto: BaseUserDto) : UserDto.LoginResponse{
        val validateUser = validateRegisterUser(userLoginDto.email, userLoginDto.password)
        val accessToken = jwtUtil.generateAccessToken(validateUser.email)
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

    private fun validateAuthor(loginUser: User, authorUser: User?) {
        if (loginUser != authorUser) {
            throw BusinessException(ErrorCode.AUTHOR_MISMATCH)
        }
    }

    fun validateUserAndAuthor(email: String, password: String, author: User?): User {
        val validatedUser = validateRegisterUser(email, password)
        validateAuthor(validatedUser, author)
        return validatedUser
    }

    private fun findUserByEmail(email: String): User? = userRepository.findByEmail(email)

    private fun findVerifiedUserByUserId(userId: Long): User =
        userRepository.findById(userId).orElseThrow { EntityNotFoundException(ErrorCode.MEMBER_NOT_EXISTS) }

    fun deleteUser(email: String, password: String) {
        val user = validateRegisterUser(email, password)
        userRepository.deleteById(user.userId!!)
    }
}
