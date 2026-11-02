package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.NewPasswordRequest;
import ru.skypro.homework.dto.UpdateUserRequest;
import ru.skypro.homework.dto.UserResponse;

@RestController
@RequestMapping("/users")
public class UserController {
    @Operation(summary = "Обновление пароля")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Пароль обновлен"),
            @ApiResponse(responseCode = "401", description = "Неверный текущий пароль", content = @Content),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен", content = @Content) })
    @PostMapping("/set_password")
    public ResponseEntity<?> setPassword(@RequestBody NewPasswordRequest newPasswordRequest) {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Получение информации об авторизованном пользователе")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Данные пользователя", content = @Content(schema = @Schema(implementation = UserResponse.class)))
            , @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content) })
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getUser() {
        return ResponseEntity.ok(new UserResponse());
    }

    @Operation(summary = "Обновление информации об авторизованном пользователе")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Данные обновлены",
            content = @Content(schema = @Schema(implementation = UpdateUserRequest.class))),
            @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content) })
    @PatchMapping("/me")
    public ResponseEntity<UpdateUserRequest> updateUser(@RequestBody UpdateUserRequest updateUserRequest) {
        return ResponseEntity.ok(updateUserRequest);
    }

    @Operation(summary = "Обновление аватара авторизованного пользователя", description = "Принимает файл изображения")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Аватар обновлен"),
            @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content) })
    @PatchMapping("/me/image")
    public ResponseEntity<?> updateUserImage(@RequestBody String image) {
        return ResponseEntity.ok().build();
    }
}
