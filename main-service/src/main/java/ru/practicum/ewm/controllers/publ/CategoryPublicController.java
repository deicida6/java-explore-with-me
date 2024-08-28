package ru.practicum.ewm.controllers.publ;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.service.CategoryService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/categories")
@Validated
@RequiredArgsConstructor
public class CategoryPublicController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getCategoryPublic(HttpServletRequest request,
                                               @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                               @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Запрос к конечной точке получен: '{} {}', строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        log.info("Запрошен список категорий  from={}, size={}", from, size);
        return categoryService.getCategoryPublic(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategoryByIdPublic(HttpServletRequest request,
                                             @Positive @PathVariable Long catId) {
        log.info("Запрос к конечной точке получен: '{} {}', строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        log.info("Запрошена категоря с catId={}", catId);
        return categoryService.getCategoryByIdPublic(catId);
    }
}