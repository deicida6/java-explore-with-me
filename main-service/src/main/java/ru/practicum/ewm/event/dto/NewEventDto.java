package ru.practicum.ewm.event.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import ru.practicum.ewm.location.model.Location;

@Data
public class NewEventDto {

    @NotNull
    @NotBlank
    @Size(min = 20, message = "Аннотация слишком короткая")
    @Size(max = 2000, message = "Аннотация слишком длинная")
    private String annotation;

    @Positive
    private Long category;

    @NotNull
    @NotBlank
    @Size(min = 20, message = "Описание слишком короткое}")
    @Size(max = 7000, message = "Описание слишком длинное")
    private String description;

    @NotNull
    @NotBlank
    private String eventDate;

    @NotNull
    private Location location;

    private Boolean paid;

    @Min(0)
    private Integer participantLimit;

    private Boolean requestModeration;

    @NotNull
    @NotBlank
    @Size(min = 3, message = "Заголовок слишком короткий")
    @Size(max = 120, message = "Заголовок слишком длинный")
    private String title;
}