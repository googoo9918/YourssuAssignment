package yourssu.yourssuAssigmnet.global.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import yourssu.yourssuAssigmnet.global.resolver.authinfo.AuthInfoArgumentResolver

@Configuration
class WebConfig(
    private val authInfoArgumentResolver: AuthInfoArgumentResolver
) : WebMvcConfigurer {

    // 커스텀 argument resolver를 등록
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        // 주입받은 `authInfoArgumentResolver`를 resolver 목록에 추가
        resolvers.add(authInfoArgumentResolver)
    }
}
