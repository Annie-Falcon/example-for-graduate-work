package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema
@Data
public class Comments {
    @Schema(description = "общее количество комментариев", example = "20")
    private Integer count;
    @Schema(description = "Комментарии")
    private Comment results;
}
