package yourssu.yourssuAssigmnet.global.config;

import org.springframework.beans.factory.annotation.Autowired
import kotlin.jvm.Throws;
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import yourssu.yourssuAssigmnet.global.jwt.JwtAuthenticationFilter
import yourssu.yourssuAssigmnet.global.jwt.JwtUtil

@Configuration
@EnableWebSecurity
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
            .anyRequest().authenticated()
            .and()
            .httpBasic()
    }
}