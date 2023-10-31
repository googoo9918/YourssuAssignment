package yourssu.yourssuAssigmnet.domain.user.controller

import io.swagger.annotations.*
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
@Api(tags = ["User Controller"], description = "사용자 관리 API")
class UserController(
    private val userService: UserService,
    private val userMapper: UserMapper
) {

    @PostMapping
    @ApiOperation(value = "회원가입", notes = "email과 password, username은 공백이면 안되며 email은 email형식을 지켜야 합니다.")
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "성공"),
            ApiResponse(code = 400, message = "이미 가입된 계정 또는 email, password, username 형식 오류"),
            ApiResponse(code = 405, message = "Request method not supported")
        ]
    )
    fun postUser(@Valid @RequestBody @ApiParam(value = "사용자 등록 정보") userPostDto: UserDto.Post): ResponseEntity<UserDto.Response> {
        val user = userService.createUser(userMapper.userPostDtoToUser(userPostDto))
        val response = userMapper.userToUserResponse(user)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping
    @ApiOperation(value = "회원탈퇴", notes = "우측 상단 자물쇠를 통해 Authorization Header를 입력해주세요!")
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "성공"),
            ApiResponse(code = 405, message = "Request method not supported")
        ]
    )
    fun deleteUser(
//        @Valid @RequestBody userDeleteDto: BaseUserDto
        @Auth authInfo: AuthInfo
    ): ResponseEntity<Void> {
        userService.deleteUser(authInfo.email)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/login")
    @ApiOperation(value = "사용자 로그인")
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "성공"),
            ApiResponse(code = 400, message = "비밀번호 불일치 또는 존재하지 않는 회원"),
            ApiResponse(code = 405, message = "Request method not supported")
        ]
    )
    fun login(@Valid @RequestBody  @ApiParam(value = "로그인 정보") userLoginDto: BaseUserDto): ResponseEntity<UserDto.LoginResponse> {
        val jwtTokenResponseDto = userService.login(userLoginDto)
        return ResponseEntity.ok(jwtTokenResponseDto)
    }

}