package ru.practicum.ewm.compilation.dto;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UpdateCompilationRequest {

    private List<Long> events;
    private boolean pinned;
    @Size(min = 1, message = "заголовок слишком короткий")
    @Size(max = 50, message = "заголовок слишком длинный}")
    private String title;
}
