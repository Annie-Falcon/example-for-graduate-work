package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentResponse;
import ru.skypro.homework.dto.CommentsResponse;
import ru.skypro.homework.dto.CreateOrUpdateCommentRequest;
import ru.skypro.homework.service.CommentService;

@RestController
@RequestMapping("/ads/{adId}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "Получение комментариев объявления")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Список комментариев",
            content = @Content(schema = @Schema(implementation = CommentsResponse.class))),
            @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Объявление не найдено", content = @Content)})
    @GetMapping
    public ResponseEntity<CommentsResponse> getComments(@PathVariable Integer adId) {
        return ResponseEntity.ok(commentService.getAllComments(adId));
    }

    @Operation(summary = "Добавление комментария к объявлению")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Комментарий добавлен",
            content = @Content(schema = @Schema(implementation = CommentResponse.class))),
            @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Объявление не найдено", content = @Content)})
    @PostMapping
    public ResponseEntity<CommentResponse> addComment(@PathVariable Integer adId,
                                                      @RequestBody CreateOrUpdateCommentRequest textComment,
                                                      Authentication auth) {
        var response = commentService.createComment(adId, textComment, auth);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Удаление комментария")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Комментарий удален"),
            @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен", content = @Content),
            @ApiResponse(responseCode = "404", description = "Комментарий не найден", content = @Content)})
    @PreAuthorize("#ad.author.email == authentication.name or hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer adId, @PathVariable Integer commentId) {
        if (!commentService.delete(commentId)) {
            return ResponseEntity.notFound().build(); // Вернёт 404 Not Found
        }
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @Operation(summary = "Обновление комментария")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Комментарий обновлен",
            content = @Content(schema = @Schema(implementation = CommentResponse.class))),
            @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен", content = @Content),
            @ApiResponse(responseCode = "404", description = "Комментарий не найден", content = @Content)})
    @PreAuthorize("#ad.author.email == authentication.name or hasAuthority('ROLE_ADMIN')")
    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Integer adId,
                                                         @PathVariable Integer commentId,
                                                         @RequestBody CreateOrUpdateCommentRequest textComment) {
        return commentService.update(commentId, textComment)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
