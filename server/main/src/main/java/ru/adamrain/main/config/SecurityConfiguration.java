package ru.adamrain.main.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ru.adamrain.main.security.UserDetailsServiceImpl;
import ru.adamrain.main.security.jwt.JwtAuthenticationEntryPoint;
import ru.adamrain.main.security.jwt.JwtTokenFilter;


import java.util.List;

@Configuration
// Аннотация @Configuration указывает, что этот класс используется для конфигурации компонентов безопасности приложения.
@EnableMethodSecurity
// Аннотация @EnableMethodSecurity включает поддержку безопасности на уровне методов (например, @PreAuthorize).
@RequiredArgsConstructor
// Аннотация @RequiredArgsConstructor автоматически создаёт конструктор для финальных полей (инициализация через внедрение зависимостей).
public class SecurityConfiguration {

    private final UserDetailsServiceImpl userDetailsService;
    // Сервис для загрузки пользовательских данных в процессе аутентификации.

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    // Компонент для обработки ошибок аутентификации (например, когда токен невалиден).

    private final JwtTokenFilter jwtTokenFilter;
    // Фильтр, который проверяет JWT-токен в запросе и выполняет аутентификацию на основе токена.

    @Bean
    // Определяем bean для шифрования паролей с использованием BCrypt.
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    // Определяем аутентификационный провайдер, который будет использовать наш UserDetailsService и BCryptPasswordEncoder.
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        // Настраиваем провайдер на использование нашего сервиса для получения данных пользователя.
        authenticationProvider.setUserDetailsService(userDetailsService);

        // Устанавливаем шифратор паролей.
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    @Bean
    // Определяем AuthenticationManager для обработки аутентификации.
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    // Настраиваем CORS для разрешения запросов с конкретных источников (в данном случае с localhost:5173).
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Определяем разрешённые источники (в данном случае localhost:5173). Можно использовать "*" для всех источников.
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));

        // Определяем разрешённые HTTP-методы.
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Определяем разрешённые заголовки.
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With"));

        // Настраиваем источник конфигурации CORS.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Разрешаем CORS для всех путей.
        return source;
    }

    @Bean
    // Основная конфигурация безопасности приложения.
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                // Включаем поддержку CORS по умолчанию.

                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/api/v1/app/**").permitAll())
                // Разрешаем доступ ко всем запросам, начиная с "/api/v1/app/**", без аутентификации.

                .exceptionHandling(configurer ->
                        configurer.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                // Устанавливаем обработчик ошибок аутентификации (в случае неправильного или отсутствующего токена).

                .csrf(AbstractHttpConfigurer::disable)
                // Отключаем защиту от CSRF-атак, так как мы используем JWT для защиты API.

                .httpBasic(Customizer.withDefaults())
                // Включаем HTTP Basic аутентификацию по умолчанию (но не используем, так как у нас JWT).

                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Указываем, что сессии не должны сохраняться (статус STATELESS), так как мы работаем с JWT-токенами.

                .authenticationProvider(authenticationProvider())
                // Устанавливаем провайдер аутентификации с нашим UserDetailsService и шифратором паролей.

                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // Добавляем наш JWT-фильтр перед стандартным фильтром UsernamePasswordAuthenticationFilter для проверки токенов.

        return http.build();
    }
}
