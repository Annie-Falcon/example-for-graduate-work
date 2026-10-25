package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema
@Data
public class Ad {

    @Schema(description = "id автора объявления", example = "1")
    private Integer author;
    @Schema(description = "Ссылка на картинку объявления", example = "https://example.com/image.jpg")
    private String image;
    @Schema(description = "id объявления", example = "134")
    private Integer pk;
    @Schema(description = "Цена объявления", example = "70000")
    private Integer price;
    @Schema(description = "Заголовок объявления", example = "Ноутбук")
    private String title;
}
