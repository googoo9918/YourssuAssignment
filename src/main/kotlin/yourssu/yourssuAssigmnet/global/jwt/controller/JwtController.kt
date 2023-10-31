package yourssu.yourssuAssigmnet.global.jwt.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import yourssu.yourssuAssigmnet.domain.user.service.UserService
import yourssu.yourssuAssigmnet.global.jwt.JwtUtil
import yourssu.yourssuAssigmnet.global.jwt.dto.TokenResponseDto
import yourssu.yourssuAssigmnet.global.jwt.service.JwtService
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/api/token")
class JwtController(
    private val jwtService: JwtService
) {

    @PostMapping("/access")
    fun refreshAccessToken(httpServletRequest : HttpServletRequest): ResponseEntity<TokenResponseDto> {
        val token = httpServletRequest.getHeader("Authorization")?.replace("Bearer ", "")
        val tokenResponseDto = jwtService.createAccessAndRefresh(token)
        return ResponseEntity.ok(tokenResponseDto)
    }
}