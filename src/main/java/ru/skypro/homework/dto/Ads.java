package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema
@Data
public class Ads {

    @Schema(description = "общее количество объявлений", example = "20")
    private Integer count;
    @Schema(description = "Объявления")
    private Ad results;
}
