package ru.practicum.ewm.compilation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class NewCompilationDto {

    private List<Long> events;
    private boolean pinned;
    @NotBlank
    @Size(min = 1, message = "заголовок слишком короткий")
    @Size(max = 50, message = "заголовок слишком длинный}")
    private String title;
}
