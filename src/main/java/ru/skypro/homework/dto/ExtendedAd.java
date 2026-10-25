package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema
@Data
public class ExtendedAd {
    @Schema(description = "id объявления", example = "134")
    private Integer pk;
    @Schema(description = "Имя автора объявления", example = "Иван")
    private String authorFirstName;
    @Schema(description = "Фамилия автора объявления", example = "Иванов")
    private String authorLastName;
    @Schema(description = "Описание объявления", example = "описание ноутбука")
    private String description;
    @Schema(description = "Логин автора объявления", example = "user@gmail.com")
    private String email;
    @Schema(description = "Ссылка на картинку объявления", example = "https://example.com/image.jpg")
    private String image;
    @Schema(description = "Телефон автора объявления", example = "+7 (999) 123-45-67")
    private String phone;
    @Schema(description = "Цена объявления", example = "70000")
    private Integer price;
    @Schema(description = "Заголовок объявления", example = "Ноутбук")
    private String title;
}
