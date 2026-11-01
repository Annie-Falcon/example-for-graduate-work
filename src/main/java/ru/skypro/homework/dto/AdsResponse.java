package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema
@Data
public class AdsResponse {
    @Schema(description = "общее количество объявлений", example = "20")
    private Integer count;
    @Schema(description = "Объявления")
    private AdResponse results;

    public AdsResponse(int size, List<AdResponse> dtoList) {
    }

    public AdsResponse() {   }
}
