package ru.practicum.ewm.event.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import ru.practicum.ewm.location.model.Location;

@Data
@Builder
public class UpdateEventAdminRequest {

    @Size(min = 20, message = "Аннотация слишком короткая")
    @Size(max = 2000, message = "Аннотация слишком длинная")
    private String annotation;

    @Positive
    private Long category;

    @Size(min = 20, message = "Описание слишком короткое}")
    @Size(max = 7000, message = "Описание слишком длинное")
    private String description;


    private String eventDate;

    private Location location;

    private Boolean paid;

    @Positive
    private Integer participantLimit;

    private Boolean requestModeration;

    private String stateAction;

    @Size(min = 3, message = "Заголовок слишком короткий")
    @Size(max = 120, message = "Заголовок слишком длинный")
    private String title;
}