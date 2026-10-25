package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class Login {
    @Schema(description = "Логин пользователя", example = "user@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;
    @Schema(description = "Пароль пользователя", example = "password123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}
