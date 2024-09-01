package ru.practicum.ewm.controllers.priv;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.participation.dto.ParticipationRequestDto;
import ru.practicum.ewm.participation.service.ParticipationService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users/{userId}/requests")
@Validated
@RequiredArgsConstructor
public class UserPrivateController {

    private final ParticipationService participationService;

    @GetMapping
    public List<ParticipationRequestDto> getParticipationRequestPrivate(HttpServletRequest request,
                                                                        @NotNull @Positive @PathVariable Long userId) {
        log.info("Запрос к конечной точке получен: '{} {}', строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        log.info("Get ParticipationRequest with userId={}", userId);
        return participationService.getParticipationRequestPrivate(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto addParticipationRequestPrivate(HttpServletRequest request,
                                                                  @Positive @PathVariable(required = false) Long userId,
                                                                  @Positive @RequestParam(required = false) Long eventId) {
        log.info("Запрос к конечной точке получен: '{} {}', строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        log.info("Создан запрос на участие с данными userId={}, eventId={}", userId, eventId);

        return participationService.addParticipationRequestPrivate(userId, eventId);
    }


    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto updateRejectedParticipationRequestPrivate(HttpServletRequest request,
                                                                             @NotNull @Positive @PathVariable Long userId,
                                                                             @NotNull @Positive @PathVariable(required = true, name = "requestId") Long requestId) {
        log.info("Запрос к конечной точке получен: '{} {}', строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        log.info("Отменен запрос на участие с данными userId={}, requestId={}", userId, requestId);
        return participationService.updateRejectedParticipationRequestPrivate(userId, requestId);
    }
}
