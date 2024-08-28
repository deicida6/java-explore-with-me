package ru.practicum.ewm.participation.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import ru.practicum.ewm.event.model.Status;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "participations")
@Builder
public class ParticipationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime created;

    @Column
    private Long event;

    @Column
    private Long requester;

    @Enumerated(EnumType.STRING)
    private Status status;
}