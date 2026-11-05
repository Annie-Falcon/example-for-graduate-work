package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordRequest;
import ru.skypro.homework.dto.UpdateUserRequest;
import ru.skypro.homework.dto.UserResponse;
import ru.skypro.homework.service.UserService;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @Operation(summary = "Обновление пароля")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Пароль обновлен"),
            @ApiResponse(responseCode = "401", description = "Неверный текущий пароль", content = @Content),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен", content = @Content)})
    @PostMapping("/set_password")
    public ResponseEntity<?> setPassword(@Valid @RequestBody NewPasswordRequest newPasswordRequest, Authentication auth) {
        if (service.changePassword(newPasswordRequest, auth)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Получение информации об авторизованном пользователе")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Данные пользователя", content = @Content(schema = @Schema(implementation = UserResponse.class)))
            , @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content)})
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getUser(Authentication auth) {
        return ResponseEntity.ok(service.getCurrentUser(auth));
    }

    @Operation(summary = "Обновление информации об авторизованном пользователе")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Данные обновлены",
            content = @Content(schema = @Schema(implementation = UpdateUserRequest.class))),
            @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content)})
    @PatchMapping("/me")
    public ResponseEntity<UpdateUserRequest> updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest,
                                                        Authentication auth) {
        service.update(updateUserRequest, auth);

        return ResponseEntity.ok(updateUserRequest);
    }

    @Operation(summary = "Обновление аватара авторизованного пользователя", description = "Принимает файл изображения")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Аватар обновлен"),
            @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content)})
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUserImage(@RequestBody MultipartFile image,
                                             Authentication auth) throws IOException {
        return service.updateAvatar(image, auth);
    }
}
