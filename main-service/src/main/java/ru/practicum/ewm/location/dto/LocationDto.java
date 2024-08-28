package ru.practicum.ewm.location.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationDto {

    private Double lat;
    private Double lon;
}
