package yourssu.yourssuAssigmnet.global.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import yourssu.yourssuAssigmnet.global.jwt.TokenManager

@Configuration
class TokenConfig {

    @Value("\${token.access-token-expiration-time}")
    lateinit var accessTokenExpirationTime: String

    @Value("\${token.refresh-token-expiration-time}")
    lateinit var refreshTokenExpirationTime: String

    @Value("\${token.secret}")
    lateinit var tokenSecret: String

    @Bean
    fun tokenManager(): TokenManager =
        TokenManager(accessTokenExpirationTime, refreshTokenExpirationTime, tokenSecret)
}