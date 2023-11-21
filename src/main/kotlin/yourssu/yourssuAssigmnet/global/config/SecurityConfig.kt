package yourssu.yourssuAssigmnet.global.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import yourssu.yourssuAssigmnet.global.jwt.JwtAuthenticationFilter
import kotlin.jvm.Throws

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter
) : WebSecurityConfigurerAdapter() {

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, "/api/members").permitAll()
            .antMatchers(HttpMethod.DELETE, "/api/members").authenticated()
            .antMatchers(HttpMethod.POST, "/api/members/login").permitAll()
            .antMatchers(
                "/swagger-ui/**", // 스웨거 UI에 대한 접근을 허용
                "/swagger-resources/**", // 스웨거 리소스에 대한 접근을 허용
                "/v3/api-docs", // 스웨거 API docs에 대한 접근을 허용
                "/webjars/**" // 스웨거 UI를 지원하는 webjars에 대한 접근을 허용
            ).permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin().disable()
            .httpBasic().disable()
    }
}
