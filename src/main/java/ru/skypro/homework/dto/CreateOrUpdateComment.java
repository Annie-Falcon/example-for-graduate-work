package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema
@Data
public class CreateOrUpdateComment {
    @Schema(description = "Текст комментария", example = "новый комментарий", requiredMode = Schema.RequiredMode.REQUIRED)
    private String text;
}
