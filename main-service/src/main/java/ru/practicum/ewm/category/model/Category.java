package ru.practicum.ewm.category.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Table(name = "category")
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 1, message = "Имя слишком короткое")
    @Size(max = 50, message = "Имя слишком длинное")
    @Column(name = "name")
    private String name;
}