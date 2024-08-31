package ru.practicum.ewm.category.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
public class CategoryDto {
    private Long id;
    private String name;
}