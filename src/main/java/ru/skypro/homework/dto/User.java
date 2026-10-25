package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class User {
    @Schema(description = "ID пользователя", example = "1")
    private Integer id;
    @Schema(description = "Email пользователя", example = "user@gmail.com")
    private String email;
    @Schema(description = "Имя пользователя", example = "Иван")
    private String firstName;
    @Schema(description = "Фамилия пользователя", example = "Иванов")
    private String lastName;
    @Schema(description = "Телефон пользователя", example = "+7 (999) 123-45-67")
    private String phone;
    @Schema(description = "Роль пользователя", allowableValues = {"USER", "ADMIN"}, example = "USER")
    private Role role;
    @Schema(description = "Ссылка на аватар", example = "https://example.com/image.jpg")
    private String image;
}
