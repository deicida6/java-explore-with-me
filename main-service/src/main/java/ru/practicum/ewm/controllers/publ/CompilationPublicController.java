package ru.practicum.ewm.controllers.publ;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.service.CompilationService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/compilations")
@Validated
@RequiredArgsConstructor
public class CompilationPublicController {

    private final CompilationService compilationService;

    @GetMapping
    public List<CompilationDto> getCompilationsPublic(HttpServletRequest request,
                                                      @RequestParam(required = false, name = "pinned") Boolean pinned,
                                                      @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                      @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Запрос к конечной точке получен: '{} {}', строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        log.info("Получили собрания с данными pinned={}, from={}, size={}", pinned, from, size);
        return compilationService.getCompilationsPublic(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompilationByIdPublic(HttpServletRequest request,
                                                   @Positive @PathVariable Long compId) {
        log.info("Запрос к конечной точке получен: '{} {}', строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        log.info("Получили собрания с данными compId={}", compId);
        return compilationService.getCompilationByIdPublic(compId);
    }

}