package com.example.demo.security;

import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserService userService;
    @Autowired
    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }


    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(userService.getPasswordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth
                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")
                        .requestMatchers("/login*", "/register*")
                        .permitAll()
                        .anyRequest()
                        .permitAll()
                )
                .formLogin(form ->
                        form
                        .loginPage("/login")
                        .defaultSuccessUrl("/abc", true)
                        .failureUrl("/nothing")
                        .failureHandler(authenticationFailureHandler())
                )
                .logout(logout ->
                        logout
                        .logoutUrl("/logout")
                        .deleteCookies("JSESSIONID")
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
        return http.build();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            String errorMessage = exception.getMessage();

            if (exception instanceof LockedException) {
                errorMessage =
                        "Your account has been locked due to multiple failed login attempts." +
                        " Please contact the administrator.";
            } else if (exception instanceof DisabledException) {
                errorMessage = "Your account has been disabled. Please contact the administrator.";
            } else if (exception instanceof BadCredentialsException) {
                errorMessage = "Invalid username or password";
            }

            response.sendRedirect("/login?error=true&message=" + errorMessage);
        };
    }

}