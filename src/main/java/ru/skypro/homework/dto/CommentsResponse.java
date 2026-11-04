package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema
@Data
public class CommentsResponse {
    @Schema(description = "общее количество комментариев", example = "20")
    private Integer count;
    @Schema(description = "Комментарии")
    private CommentResponse results;

    public CommentsResponse(int size, List<CommentResponse> dtoList) {  }

    public CommentsResponse() {  }
}
