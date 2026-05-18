package fr.eni.cave.exceptions;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import fr.eni.cave.dto.ResponseApi;

import java.util.Locale;

@ControllerAdvice
@AllArgsConstructor
public class AppExceptionHandler {

    private MessageSource messageSource;

    @ExceptionHandler(value = { HttpRequestMethodNotSupportedException.class })
    public ResponseEntity<ResponseApi<String>> methodNotAllowedException(
            HttpRequestMethodNotSupportedException Exception,
            Locale locale
    ) {
        String errorMessage = messageSource.getMessage(
                "validation.requests.httpRequestMethodNotSupportedException",
                null,
                locale
        );

        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(
                        ResponseApi.<String>builder()
                                .statusCode(HttpStatus.NOT_ACCEPTABLE.value())
                                .message(HttpStatus.NOT_ACCEPTABLE.name())
                                .data(errorMessage)
                                .build()
                );
    }
}