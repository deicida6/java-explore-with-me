package ru.practicum.ewm.controllers.priv;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comment.dto.CommentDto;
import ru.practicum.ewm.comment.service.CommentService;

import java.util.List;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
public class CommentPrivateController {

    private final CommentService commentService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/events/{eventId}/comments")
    public CommentDto addComment(HttpServletRequest request,
                                 @PathVariable Long eventId,
                                 @NonNull @RequestBody CommentDto commentDto) {
        log.info("Получен запрос к эндпоинту: '{} {}', строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        log.info("Добавление комментария к событию eventId={}", eventId);
        return commentService.addCommentPrivate(eventId, commentDto);
    }

    @GetMapping("/events/{eventId}/comments")
    public List<CommentDto> getComments(HttpServletRequest request,
                                        @PathVariable Long eventId,
                                        @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                        @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Получен запрос к эндпоинту: '{} {}', строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        log.info("Получение комментариев по событию eventId={}, from={}, size={}", eventId, from, size);
        return commentService.getCommentsPrivate(eventId, from, size);
    }

    @GetMapping("/comments/{commentId}")
    public CommentDto getCommentById(HttpServletRequest request,
                                     @Positive @PathVariable Long commentId) {
        log.info("Получен запрос к эндпоинту: '{} {}', строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        log.info("Получение комментария по Id ={}", commentId);
        return commentService.getCommentByIdPrivate(commentId);
    }

    @PatchMapping("/comments/{commentId}")
    public CommentDto updateCommentPrivate(HttpServletRequest request,
                                           @Positive @PathVariable Long commentId,
                                           @Valid @RequestBody CommentDto commentDto) {
        log.info("Получен запрос к эндпоинту: '{} {}', строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        log.info("Обновление комментария с commentId={}", commentId);
        return commentService.updateCommentPrivate(commentId, commentDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(HttpServletRequest request,
                              @Positive @PathVariable Long commentId) {
        log.info("Получен запрос к эндпоинту: '{} {}', строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        log.info("Удаление комментария с commentId={}", commentId);
        commentService.deleteCommentByIdPrivate(commentId);
    }
}
