package ru.practicum.ewm.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NewUserRequest {

    @NotBlank
    @Email
    @Size(min = 6, message = "Почта слишком короткая")
    @Size(max = 254, message = "Почта слишком длинная")
    private String email;

    @NotBlank
    @Size(min = 2, message = "Имя слишком короткое")
    @Size(max = 250, message = "Имя слишком длинное")
    private String name;
}