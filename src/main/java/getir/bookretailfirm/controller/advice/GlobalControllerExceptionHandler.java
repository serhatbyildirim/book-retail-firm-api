package getir.bookretailfirm.controller.advice;

import getir.bookretailfirm.exception.AuthenticationFailedException;
import getir.bookretailfirm.exception.NotFoundException;
import getir.bookretailfirm.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ErrorResponse> handle(AuthenticationFailedException exception) {
        log.error("Authentication failed exception occurred.", exception);
        ErrorResponse errorResponse = new ErrorResponse(
                "AuthenticationFailedException",
                System.currentTimeMillis(),
                Collections.singletonList(exception.getMessage())
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(NotFoundException exception) {
        log.error("Get data failed exception occurred.", exception);
        ErrorResponse errorResponse = new ErrorResponse(
                "NotFoundException",
                System.currentTimeMillis(),
                Collections.singletonList(exception.getMessage())
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handle(ConstraintViolationException exception) {
        List<String> errorMessages = exception.getConstraintViolations().stream()
                .map(violation -> this.getMessage(violation.getMessageTemplate(), violation.getInvalidValue()))
                .collect(Collectors.toList());
        ErrorResponse errorResponse = new ErrorResponse(
                "ConstraintViolationException",
                System.currentTimeMillis(),
                errorMessages
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException exception) {
        List<String> errorMessages = exception.getBindingResult().getFieldErrors().stream()
                .map(this::getMessage)
                .collect(Collectors.toList());

        ErrorResponse errorResponse = new ErrorResponse(
                "MethodArgumentNotValidException",
                System.currentTimeMillis(),
                errorMessages
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handle(AccessDeniedException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                "AccessDeniedException",
                System.currentTimeMillis(),
                Collections.singletonList(exception.getMessage())
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException() {
        ErrorResponse errorResponse = new ErrorResponse(
                "GenericException",
                System.currentTimeMillis(),
                Collections.singletonList("Generic exception occurred.")
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getMessage(FieldError error) {
        return getMessage(error.getDefaultMessage(), error.getArguments());
    }

    private String getMessage(String messageKey, Object... args) {
        return messageSource.getMessage(
                messageKey, args, messageKey, LocaleContextHolder.getLocale()
        );
    }

}
