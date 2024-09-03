package ru.practicum.ewm.controllers.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comment.dto.CommentDto;
import ru.practicum.ewm.comment.service.CommentService;

@Slf4j
@RestController
@RequestMapping(path = "/admin/comments/{commentId}")
@RequiredArgsConstructor
@Validated
public class CommentAdminController {

    private final CommentService commentService;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void deleteCommentAdmin(HttpServletRequest request,
                                @Positive @PathVariable Long commentId) {
        log.info("Request to the endpoint was received: '{} {}', string of request parameters: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        commentService.deleteCommentByIdAdmin(commentId);

    }

    @PatchMapping()
    public CommentDto updateCommentAdmin(HttpServletRequest request,
                                         @Positive @PathVariable Long commentId,
                                         @Valid @RequestBody CommentDto commentDto) {
        log.info("Request to the endpoint was received: '{} {}', string of request parameters: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        log.info("Update comment  status with commentId={} ", commentId);
        return commentService.updateCommentAdmin(commentId, commentDto);
    }
}