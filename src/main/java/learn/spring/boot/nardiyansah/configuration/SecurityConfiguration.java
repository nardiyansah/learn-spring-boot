package learn.spring.boot.nardiyansah.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(
                request -> request.antMatchers("/", "/error", "/webjars/**").permitAll()
                        .anyRequest().authenticated()
        ).csrf(
               httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        ).logout(
                httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.logoutSuccessUrl("/").permitAll()
        ).exceptionHandling(
                error -> error.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
        ).oauth2Login();
    }
}
