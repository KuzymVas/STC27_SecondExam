package org.innopolis.kuzymvas.secondexam.config;

import org.innopolis.kuzymvas.secondexam.secuirty.JPAAuthenticationProvider;
import org.innopolis.kuzymvas.secondexam.secuirty.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final RequestMatcher PUBLIC_URLS = new AntPathRequestMatcher("/user/register");
    private static final RequestMatcher PROTECTED_URLS = new NegatedRequestMatcher(PUBLIC_URLS);

    private final RestAuthenticationEntryPoint authenticationEntryPoint;
    private final JPAAuthenticationProvider provider;

    @Autowired
    public SecurityConfig(
            RestAuthenticationEntryPoint authenticationEntryPoint,
            JPAAuthenticationProvider provider) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.provider = provider;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(provider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // Настраиваем аутентификацию по HTTP Basic
                .httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .authorizeRequests()
                // Для публичных URL даем доступ всем
                .requestMatchers(PUBLIC_URLS)
                .permitAll()
                // Для защищенных  - только прошедшим аутентификацию
                .requestMatchers(PROTECTED_URLS)
                .authenticated()
                .and()
                // Отключаем аутентификацию по форме, логаут и csrf
                .formLogin().disable()
                .logout().disable()
                .csrf().disable();
    }
}
