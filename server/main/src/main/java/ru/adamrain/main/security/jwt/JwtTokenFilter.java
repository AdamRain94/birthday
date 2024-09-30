package ru.adamrain.main.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.adamrain.main.security.UserDetailsServiceImpl;

import java.io.IOException;

//Этот фильтр обрабатывает каждый HTTP-запрос, проверяя наличие JWT-токена в заголовке запроса,
//и если токен валиден, устанавливает аутентификацию пользователя в контексте безопасности Spring.
@RequiredArgsConstructor
@Slf4j
@Component
// Класс JwtTokenFilter отвечает за фильтрацию каждого HTTP-запроса, проверяя наличие JWT токена.
public class JwtTokenFilter extends OncePerRequestFilter {

    // Внедряем зависимости: утилиты для работы с JWT и сервис для загрузки данных о пользователе.
    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    // Метод doFilterInternal обрабатывает каждый запрос, проверяя наличие валидного JWT токена.
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // Получаем токен из заголовков запроса.
            String jwtToken = getToken(request);
            // Если токен существует и является валидным, выполняем аутентификацию пользователя.
            if(jwtToken != null && jwtUtils.validate(jwtToken)){
                // Извлекаем телефон пользователя из токена.
                String tel = jwtUtils.getUserTel(jwtToken);
                // Загружаем данные о пользователе по номеру телефона.
                UserDetails userDetails = userDetailsService.loadUserByUsername(tel);

                // Создаем объект аутентификации с данными пользователя.
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                // Устанавливаем дополнительные детали аутентификации, связанные с запросом.
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Устанавливаем аутентификацию в контексте безопасности.
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception e) {
            // Логируем ошибки аутентификации.
            log.error("Cannot set user authentication: {}", e.getMessage());
        }
        // Продолжаем выполнение цепочки фильтров.
        filterChain.doFilter(request, response);
    }

    // Метод для извлечения токена из заголовка Authorization.
    private String getToken(HttpServletRequest request){
        // Получаем значение заголовка Authorization.
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        // Проверяем, что заголовок содержит текст и начинается с "Bearer ".
        if(StringUtils.hasText(header) && header.startsWith("Bearer ")){
            // Возвращаем сам токен, без префикса "Bearer ".
            return header.substring(7);
        }
        return null; // Если токена нет, возвращаем null.
    }
}
