package ru.practicum.ewm.dto.stats;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewStats {

    private String app;
    private String uri;
    private Integer hits;

}