package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class Register {

    @Schema(description = "Логин пользователя", example = "user@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;
    @Schema(description = "Пароль пользователя", example = "password123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
    @Schema(description = "Имя пользователя", example = "Иван", requiredMode = Schema.RequiredMode.REQUIRED)
    private String firstName;
    @Schema(description = "Фамилия пользователя", example = "Иванов", requiredMode = Schema.RequiredMode.REQUIRED)
    private String lastName;
    @Schema(description = "Телефон пользователя", example = "+7(995)777-77-77", requiredMode = Schema.RequiredMode.REQUIRED)
    private String phone;
    @Schema(description = "Роль пользователя", allowableValues = {"USER", "ADMIN"}, example = "USER", requiredMode = Schema.RequiredMode.REQUIRED)
    private Role role;
}
