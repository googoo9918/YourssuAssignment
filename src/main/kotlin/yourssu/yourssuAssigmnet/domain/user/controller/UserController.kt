package yourssu.yourssuAssigmnet.domain.user.controller

import yourssu.yourssuAssigmnet.domain.common.dto.BaseUserDto
import yourssu.yourssuAssigmnet.domain.user.dto.UserDto
import yourssu.yourssuAssigmnet.domain.user.entity.User
import yourssu.yourssuAssigmnet.domain.user.mapper.UserMapper
import yourssu.yourssuAssigmnet.domain.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import yourssu.yourssuAssigmnet.global.resolver.authinfo.Auth
import yourssu.yourssuAssigmnet.global.resolver.authinfo.AuthInfo
import javax.validation.Valid

@RestController
@RequestMapping("/api/members")
@Validated
class UserController(
    private val userService: UserService,
    private val userMapper: UserMapper
) {

    @PostMapping
    fun postUser(@Valid @RequestBody userPostDto: UserDto.Post): ResponseEntity<UserDto.Response> {
        val user = userService.createUser(userMapper.userPostDtoToUser(userPostDto))
        val response = userMapper.userToUserResponse(user)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping
    fun deleteUser(
//        @Valid @RequestBody userDeleteDto: BaseUserDto
        @Auth authInfo: AuthInfo
    ): ResponseEntity<Void> {
        userService.deleteUser(authInfo.email)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody userLoginDto: BaseUserDto): ResponseEntity<UserDto.LoginResponse> {
        val jwtTokenResponseDto = userService.login(userLoginDto)
        return ResponseEntity.ok(jwtTokenResponseDto)
    }

}