package ru.practicum.ewm.exception;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ApiError> handle(Exception ex) throws IOException {
        ApiError apiError = ApiError.builder()
                .errors(Collections.singletonList(error(ex)))
                .status(HttpStatus.BAD_REQUEST)
                .reason("Некорректный запрос")
                .message(ex.getLocalizedMessage())
                .timestamp((LocalDateTime.now()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    public @NotNull
    ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                        @NonNull HttpHeaders headers,
                                                        @NonNull HttpStatus status,
                                                        @NonNull WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField());
        }
        ApiError apiError = ApiError.builder()
                .errors(errors)
                .status(HttpStatus.BAD_REQUEST)
                .reason("Запрашиваемый объект не найден")
                .message(ex.getLocalizedMessage())
                .timestamp((LocalDateTime.now()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getMessage());
        }
        ApiError apiError = ApiError.builder()
                .errors(errors)
                .status(HttpStatus.BAD_REQUEST)
                .reason("Запрашиваемый объект не найден")
                .message(ex.getLocalizedMessage())
                .timestamp((LocalDateTime.now()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }


    private String error(Exception e) throws IOException {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace();
        String error = sw.toString();
        sw.close();
        pw.close();
        return error;
    }


    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)   // для всех ситуаций, если искомый объект не найден
    public ApiError handle(final NotFoundException e) throws IOException {
        return ApiError.builder()
                .errors(Collections.singletonList(error(e)))
                .status(HttpStatus.NOT_FOUND)
                .reason("Запрашиваемый объект не найден")
                .message(e.getLocalizedMessage())
                .timestamp((LocalDateTime.now()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }

    @ExceptionHandler(ValidationExceptionFindCategory.class)
    @ResponseStatus(HttpStatus.CONFLICT)   // Категория существует
    public ApiError handle(final ValidationExceptionFindCategory e) throws IOException {
        return ApiError.builder()
                .errors(Collections.singletonList(error(e)))
                .status(HttpStatus.CONFLICT)
                .reason("Для запрошенной операции условия не выполнены")
                .message(e.getLocalizedMessage())
                .timestamp((LocalDateTime.now()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }

    @ExceptionHandler(DuplicateNameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)  //если есть дубликат Name.
    public ApiError handleThrowable(final DuplicateNameException e) throws IOException {
        return ApiError.builder()
                .errors(Collections.singletonList(error(e)))
                .status(HttpStatus.CONFLICT)
                .reason("Произошло дублирование имени")
                .message(e.getLocalizedMessage())
                .timestamp((LocalDateTime.now()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }

    @ExceptionHandler(DuplicateEmailException.class)
    @ResponseStatus(HttpStatus.CONFLICT)  //если есть дубликат Email.
    public ApiError handleThrowable(final DuplicateEmailException e) throws IOException {
        return ApiError.builder()
                .errors(Collections.singletonList(error(e)))
                .status(HttpStatus.CONFLICT)
                .reason("Произошло дублирование почты")
                .message(e.getLocalizedMessage())
                .timestamp((LocalDateTime.now()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }

    @ExceptionHandler(EventDateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleThrowable(final EventDateException e) throws IOException {
        return ApiError.builder()
                .errors(Collections.singletonList(error(e)))
                .status(HttpStatus.CONFLICT)
                .reason("Некорректное время")
                .message(e.getLocalizedMessage())
                .timestamp((LocalDateTime.now()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }

    @ExceptionHandler(StateArgumentException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleThrowable(final StateArgumentException e) throws IOException {
        return ApiError.builder()
                .errors(Collections.singletonList(error(e)))
                .status(HttpStatus.CONFLICT)
                .reason("Запрашиваемый объект не найден")
                .message(e.getLocalizedMessage())
                .timestamp((LocalDateTime.now()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }

    @ExceptionHandler(OverflowLimitException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleThrowable(final OverflowLimitException e) throws IOException {
        return ApiError.builder()
                .errors(Collections.singletonList(error(e)))
                .status(HttpStatus.CONFLICT)
                .reason("Достигнут лимит памяти")
                .message(e.getLocalizedMessage())
                .timestamp((LocalDateTime.now()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }

    @ExceptionHandler(StatusParticipationRequestException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleThrowable(final StatusParticipationRequestException e) throws IOException {
        return ApiError.builder()
                .errors(Collections.singletonList(error(e)))
                .status(HttpStatus.CONFLICT)
                .reason("Получен статус NOT PENDING")
                .message(e.getLocalizedMessage())
                .timestamp((LocalDateTime.now()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }

    @ExceptionHandler(RepeatParticipationRequestException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleThrowable(final RepeatParticipationRequestException e) throws IOException {
        return ApiError.builder()
                .errors(Collections.singletonList(error(e)))
                .status(HttpStatus.CONFLICT)
                .reason("Повтор запроса")
                .message(e.getLocalizedMessage())
                .timestamp((LocalDateTime.now()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }

}