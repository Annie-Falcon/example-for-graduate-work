package ru.skypro.homework.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final CustomUserDetailsManager customUserDetailsService;

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs",
            "/webjars/**",
            "/login",
            "/register"
    };

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       UserDetailsService userDetailsService,
                                                       PasswordEncoder passwordEncoder) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() { // настраиваем политику CORS для фронта
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000"));  // разрешаем кросс-доменные запросы только с этого адреса
        config.setAllowedMethods( // разрешаем фронтенду выполнять основные HTTP-методы, а также предполетные запросы OPTIONS.
                List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*")); // разрешаем фронтенду отправлять любые заголовки
        config.setAllowCredentials(true);  // разрешаем браузеру отправлять куки

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // применяем эти правила ко всем эндпоинтам приложения
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // отключаем защиту CSRF
                .cors().and() // включаем поддержку CORS
                .authorizeHttpRequests(
                        authorization ->
                                authorization
                                        // разрешаем маршруты для Swagger и авторизации
                                        .mvcMatchers(AUTH_WHITELIST).permitAll()
                                        // /ads/me должен быть закрыт, поэтому он идёт раньше
                                        .mvcMatchers(HttpMethod.GET, "/ads/me").authenticated()
                                        // изменение данных (создание, редактирование, удаление) — только для авторизованных
                                        .mvcMatchers(HttpMethod.POST, "/users/**", "/ads", "/ads/**").authenticated()
                                        .mvcMatchers(HttpMethod.PUT, "/ads/**").authenticated()
                                        .mvcMatchers(HttpMethod.DELETE, "/ads/**").authenticated()
                                        // публичный просмотр объявлений
                                        .mvcMatchers(HttpMethod.GET, "/ads").permitAll()
                                        .anyRequest().authenticated()// для остальных запросов требуем аутентификации
                )
                .httpBasic(withDefaults());

        return http.build();
    }
}
