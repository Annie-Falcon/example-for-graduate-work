package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentResponse;
import ru.skypro.homework.dto.CommentsResponse;
import ru.skypro.homework.dto.CreateOrUpdateCommentRequest;

@RestController
@RequestMapping("/ads/{adId}/comments")
public class CommentController {
    @Operation(summary = "Получение комментариев объявления")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Список комментариев",
            content = @Content(schema = @Schema(implementation = CommentsResponse.class))),
            @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Объявление не найдено", content = @Content) })
    @GetMapping
    public ResponseEntity<CommentsResponse> getComments(@PathVariable Integer adId) {
        return ResponseEntity.ok(new CommentsResponse());
    }

    @Operation(summary = "Добавление комментария к объявлению")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Комментарий добавлен",
            content = @Content(schema = @Schema(implementation = CommentResponse.class))),
            @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Объявление не найдено", content = @Content) })
    @PostMapping
    public ResponseEntity<CommentResponse> addComment(@PathVariable Integer adId,
                                                      @RequestBody CreateOrUpdateCommentRequest textComment) {
        return ResponseEntity.ok(new CommentResponse());
    }

    @Operation(summary = "Удаление комментария")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Комментарий удален"),
            @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен", content = @Content),
            @ApiResponse(responseCode = "404", description = "Комментарий не найден", content = @Content) })
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer adId, @PathVariable Integer commentId) {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Обновление комментария")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Комментарий обновлен",
            content = @Content(schema = @Schema(implementation = CommentResponse.class))),
            @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен", content = @Content),
            @ApiResponse(responseCode = "404", description = "Комментарий не найден", content = @Content) })
    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Integer adId,
                                                         @PathVariable Integer commentId,
                                                         @RequestBody CreateOrUpdateCommentRequest textComment) {
        return ResponseEntity.ok(new CommentResponse());
    }

}
