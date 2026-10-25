package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema
@Data
public class UpdateUser {
    @Schema(description = "Имя пользователя", example = "Иван", requiredMode = Schema.RequiredMode.REQUIRED)
    private String firstName;
    @Schema(description = "Фамилия пользователя", example = "Иванов", requiredMode = Schema.RequiredMode.REQUIRED)
    private String lastName;
    @Schema(description = "Телефон пользователя", example = "+7(995)777-77-77", requiredMode = Schema.RequiredMode.REQUIRED)
    private String phone;
}
