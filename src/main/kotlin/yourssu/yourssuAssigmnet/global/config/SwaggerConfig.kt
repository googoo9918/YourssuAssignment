package yourssu.yourssuAssigmnet.global.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.ApiKey
import springfox.documentation.service.AuthorizationScope
import springfox.documentation.service.SecurityReference
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import yourssu.yourssuAssigmnet.global.resolver.authinfo.AuthInfo

/**
 * 스웨거 설정 클래스
 */
@Configuration
class SwaggerConfig {

    /**
     * 스웨거 설정에 핵심이 되는 Bean
     */
    @Bean
    fun api(): Docket = Docket(DocumentationType.OAS_30)
        .select()
        .apis(RequestHandlerSelectors.basePackage("yourssu.yourssuAssigmnet")) // API 패키지 경로
        .paths(PathSelectors.ant("/api/**")) // path 조건에 따라서 API 문서화(ex /api/**)
        .build()
        .apiInfo(apiInfo()) // API 문서에 대한 정보 추가(최상단부 제목 부분)
        .useDefaultResponseMessages(false) // swagger에서 제공하는 기본 응답 코드 설명 제거 (ex, 403 Forbidden)
        .securityContexts(listOf(securityContext()))
        .securitySchemes(listOf(apiKey()))
        // security 설정으로 swagger에서 자물쇠 버튼을 클릭, Authrization 헤더를 넣어줄 수 있게됨
        .ignoredParameterTypes(AuthInfo::class.java)
    // 현재 @AuthInfo는 직접 입력하는게 아니라 토큰에서 가져오는 값이기에 무시처리

    private fun apiInfo(): ApiInfo = ApiInfoBuilder()
        .title("유어슈 인큐베이팅 API 문서")
        .description("유어슈 인큐베이팅 2주차까지의 API 문서입니다.")
        .version("1.0")
        .build()

    /**
     * swagger에 authorizaion 헤더를 입력할 수 있게 제작
     */
    private fun securityContext(): SecurityContext = SecurityContext.builder()
        .securityReferences(defaultAuth())
        .build()

    private fun defaultAuth(): List<SecurityReference> {
        val authorizationScope = AuthorizationScope("global", "accessEverything")
        val authorizationScopes = arrayOf(authorizationScope)
        return listOf(SecurityReference("Authorization", authorizationScopes))
    }

    private fun apiKey(): ApiKey = ApiKey("Authorization", "Authorization", "header")
}
