package ru.adamrain.main.web.handler;

import org.springframework.http.HttpStatus; // Импорт для работы с кодами состояния HTTP.
import org.springframework.http.ResponseEntity; // Импорт для создания ответа с телом.
import org.springframework.web.bind.annotation.ExceptionHandler; // Импорт для обработки исключений.
import org.springframework.web.bind.annotation.RestControllerAdvice; // Импорт для обработки исключений в контроллерах.
import org.springframework.web.context.request.WebRequest; // Импорт для работы с запросами.
import ru.adamrain.main.exception.AlreadyExistException; // Импорт пользовательского исключения, если объект уже существует.
import ru.adamrain.main.exception.EntityNotFoundException; // Импорт исключения, если сущность не найдена.
import ru.adamrain.main.exception.RefreshTokenException; // Импорт исключения для токенов обновления.

@RestControllerAdvice // Аннотация для обработки исключений во всех контроллерах.
public class WebAppExceptionHandler {

    // Обработчик для исключения RefreshTokenException.
    @ExceptionHandler(value = RefreshTokenException.class)
    public ResponseEntity<ErrorResponseBody> errorResponseBodyResponseEntity(RefreshTokenException exception, WebRequest webRequest) {
        // Создаем и возвращаем ответ с кодом 403 (FORBIDDEN).
        return buildResponse(HttpStatus.FORBIDDEN, exception, webRequest);
    }

    // Обработчик для исключения AlreadyExistException.
    @ExceptionHandler(value = AlreadyExistException.class)
    public ResponseEntity<ErrorResponseBody> alreadyExistsHandler(AlreadyExistException exception, WebRequest webRequest) {
        // Создаем и возвращаем ответ с кодом 400 (BAD REQUEST).
        return buildResponse(HttpStatus.BAD_REQUEST, exception, webRequest);
    }

    // Обработчик для исключения EntityNotFoundException.
    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseBody> notFoundHandler(EntityNotFoundException exception, WebRequest webRequest) {
        // Создаем и возвращаем ответ с кодом 404 (NOT FOUND).
        return buildResponse(HttpStatus.NOT_FOUND, exception, webRequest);
    }

    // Метод для построения ответа на основе кода состояния и исключения.
    private ResponseEntity<ErrorResponseBody> buildResponse(HttpStatus httpStatus, Exception exception, WebRequest webRequest) {
        return ResponseEntity.status(httpStatus) // Устанавливаем статус ответа.
                .body(ErrorResponseBody.builder() // Создаем тело ответа.
                        .message(exception.getMessage()) // Добавляем сообщение об ошибке.
                        .description(webRequest.getDescription(false)) // Добавляем описание запроса.
                        .build());
    }
}
