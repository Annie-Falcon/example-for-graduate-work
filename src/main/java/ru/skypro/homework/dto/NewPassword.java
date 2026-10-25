package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema
@Data
public class NewPassword {

    @Schema(description = "Текущий пароль пользователя", example = "password123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String currentPassword;
    @Schema(description = "Новый пароль пользователя", example = "password321", requiredMode = Schema.RequiredMode.REQUIRED)
    private String newPassword;
}
