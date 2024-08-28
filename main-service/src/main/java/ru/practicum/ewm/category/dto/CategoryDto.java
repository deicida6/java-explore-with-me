package ru.practicum.ewm.category.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
public class CategoryDto {
    private Long id;
    @Size(min = 1, message = "Имя слишком короткое")
    @Size(max = 50, message = "Имя слишком длинное")
    private String name;
}