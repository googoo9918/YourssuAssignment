package yourssu.yourssuAssigmnet.global.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import yourssu.yourssuAssigmnet.global.resolver.authinfo.AuthInfoArgumentResolver

@Configuration
class WebConfig(
    private val authInfoArgumentResolver: AuthInfoArgumentResolver
) : WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(authInfoArgumentResolver)
    }
}