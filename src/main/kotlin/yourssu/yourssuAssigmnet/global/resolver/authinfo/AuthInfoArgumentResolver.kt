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

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.parameterType.isAssignableFrom(AuthInfo::class.java) &&
                parameter.hasParameterAnnotation(Auth::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        modelAndViewContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        webDataBinderFactory: WebDataBinderFactory?
    ): Any? {
        val token = webRequest.getHeader("Authorization")?.replace("Bearer ", "")
            ?: throw IllegalArgumentException("Authorization header is missing or malformed")

        val email = jwtUtil.getEmailFromToken(token)
        return AuthInfo(email)
    }
}
