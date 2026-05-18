package fr.eni.cave.exceptions;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import fr.eni.cave.dto.ResponseApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@ControllerAdvice
@AllArgsConstructor
public class AppExceptionHandler {

    private MessageSource messageSource;

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<ResponseApi<String>> allException(
            Exception exception,
            Locale locale
    ) {
        String errorMessage = messageSource.getMessage(
                "notvalidexception",
                null,
                locale
        );

        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(
                        ResponseApi.<String>builder()
                                .statusCode(HttpStatus.NOT_ACCEPTABLE.value())
                                .message(HttpStatus.NOT_ACCEPTABLE.name())
                                .data(errorMessage + exception.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    public ResponseEntity<ResponseApi<List<String>>> methodArgumentNotValidException(
            MethodArgumentNotValidException exception,
            Locale locale
    ) {
        String errorMessage = messageSource.getMessage(
                "notvalidexception",
                null,
                locale
        );

        List<String> messages = new ArrayList<>();
        for (FieldError error : exception.getFieldErrors()) {
            messages.add(error.getDefaultMessage());
        }

        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(
                        ResponseApi.<List<String>>builder()
                                .statusCode(HttpStatus.NOT_ACCEPTABLE.value())
                                .message(HttpStatus.NOT_ACCEPTABLE.name())
                                .data(messages)
                                .build()
                );
    }
}