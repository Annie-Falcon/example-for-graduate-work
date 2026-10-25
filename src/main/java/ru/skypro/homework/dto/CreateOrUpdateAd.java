package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema
@Data
public class CreateOrUpdateAd {
    @Schema(description = "Заголовок объявления", example = "Ноутбук", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;
    @Schema(description = "Цена объявления", example = "70000", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer price;
    @Schema(description = "Описание объявления", example = "описание ноутбука", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;
}
