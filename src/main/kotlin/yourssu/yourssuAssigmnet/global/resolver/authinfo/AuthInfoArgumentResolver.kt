package yourssu.yourssuAssigmnet.global.resolver.authinfo

import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import yourssu.yourssuAssigmnet.global.jwt.JwtUtil

@Component
class AuthInfoArgumentResolver(
    private val jwtUtil: JwtUtil
) : HandlerMethodArgumentResolver {

    // Resolver가 특정 파라미터를 지원하는지 여부를 반환
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        // 파라미터 타입이 AuthInfo와 동일하고, @Auth 애노테이션이 해당 파라미터에 존재하는 경우 true를 반환
        return parameter.parameterType.isAssignableFrom(AuthInfo::class.java) &&
            parameter.hasParameterAnnotation(Auth::class.java)
    }

    // 실제로 파라미터 값을 반환하는 로직을 포함
    override fun resolveArgument(
        parameter: MethodParameter,
        modelAndViewContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        webDataBinderFactory: WebDataBinderFactory?
    ): Any? {
        // "Authorization" 헤더 값을 가져와서 "Bearer "를 제거. 만약 해당 헤더가 없거나 형식이 잘못된 경우 예외 발생
        val token = webRequest.getHeader("Authorization")?.replace("Bearer ", "")
            ?: throw IllegalArgumentException("Authorization header is missing or malformed")

        // 토큰에서 email 값을 추출
        val email = jwtUtil.getEmailFromToken(token)
        // 추출된 email 값을 사용하여 AuthInfo 객체를 반
        return AuthInfo(email)
    }
}
