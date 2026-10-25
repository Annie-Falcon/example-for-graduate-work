package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema
@Data
public class Comment {

    @Schema(description = "id автора объявления", example = "1")
    private Integer author;
    @Schema(description = "Ссылка на аватар автора комментария", example = "https://example.com/image.jpg")
    private String authorImage;
    @Schema(description = "Имя создателя комментария", example = "Иван")
    private String authorFirstName;
    @Schema(description = "Дата и время создания комментария в миллисекундах", example = "01:10:11 01.01.2026")
    private Integer createdAt;
    @Schema(description = "id комментария", example = "134")
    private Integer pk;
    @Schema(description = "Текст комментария", example = "комментарий новый")
    private String text;
}
