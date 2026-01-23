package com.example.food_store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

import com.example.food_store.service.impl.CustomOAuth2UserService;
import com.example.food_store.service.impl.CustomUserDetailsService;
import com.example.food_store.service.impl.UserService;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

        private CustomOAuth2AuthenticationFailureHandler customFailureHandler;

        @Bean
        PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        UserDetailsService userDetailsService(UserService userService) {
                return new CustomUserDetailsService(userService);
        }

        @Bean
        DaoAuthenticationProvider authProvider(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
                DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
                authProvider.setUserDetailsService(userDetailsService);
                authProvider.setPasswordEncoder(passwordEncoder);
                authProvider.setHideUserNotFoundExceptions(false);
                return authProvider;
        }

        @Bean
        AuthenticationSuccessHandler customSuccessHandler(UserService userService) {
                return new CustomSuccessHandler(userService);
        }

        @Bean
        SpringSessionRememberMeServices rememberMeServices() {
                SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();
                // optionally customize
                rememberMeServices.setAlwaysRemember(true);
                return rememberMeServices;
        }

        // SecurityFilterChain riêng cho /gemini-proxy với @Order(1) để xử lý trước
        @Bean
        @Order(1)
        SecurityFilterChain geminiSecurityFilterChain(HttpSecurity http) throws Exception {
                http
                                .securityMatcher("/gemini-proxy", "/gemini-proxy/**")
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                return http.build();
        }

        @Bean
        @Order(2)
        SecurityFilterChain filterChain(HttpSecurity http, UserService userService) throws Exception {
                http
                                .csrf(csrf -> csrf
                                                // Các endpoint khác vẫn yêu cầu CSRF protection
                                                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                                .authorizeHttpRequests(authorize -> authorize
                                                .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.INCLUDE)
                                                .permitAll()
                                                .requestMatchers("/product/**", "/", "/password/**", "/login/**",
                                                                "/client/**", "/css/**", "/js/**", "/register/**",
                                                                "/products/**", "/images/**", "/send-request-to-mail",
                                                                "reset-password/**", "/process-reset-password/**",
                                                                "/verify/**", "/test-Gemini")
                                                .permitAll()
                                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                                .anyRequest().authenticated())
                                .oauth2Login(oauth2 -> oauth2.loginPage("/login")
                                                .successHandler(customSuccessHandler(userService))
                                                .failureHandler(customFailureHandler)
                                                .userInfoEndpoint(user -> user
                                                                .userService(new CustomOAuth2UserService(userService))))
                                .sessionManagement((sessionManagement) -> sessionManagement
                                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                                                .invalidSessionUrl("/logout?expired").maximumSessions(1)
                                                .maxSessionsPreventsLogin(false))
                                .logout(logout -> logout.deleteCookies("JSESSIONID").invalidateHttpSession(true))
                                .rememberMe(r -> r.rememberMeServices(rememberMeServices()))
                                .formLogin(formLogin -> formLogin.loginPage("/login").failureUrl("/login?error")
                                                .successHandler(customSuccessHandler(userService)).permitAll())
                                .exceptionHandling(ex -> ex.accessDeniedPage("/access-deny"));
                return http.build();
        }

}
