package ru.practicum.ewm.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewCategoryDto {
    @NotBlank
    @Size(min = 1, message = "Имя слишком короткое")
    @Size(max = 50, message = "Имя слишком длинное")
    private String name;
}