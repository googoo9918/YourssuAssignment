package yourssu.yourssuAssigmnet.global.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import yourssu.yourssuAssigmnet.global.interceptor.AuthenticationInterceptor

@Configuration
class WebConfig(private val authenticationInterceptor: AuthenticationInterceptor) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(authenticationInterceptor)
            .order(1)
            .addPathPatterns("/api/**")
            .excludePathPatterns("/api/members/login", "/api/members/new")
    }
}