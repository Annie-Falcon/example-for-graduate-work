package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.AdResponse;
import ru.skypro.homework.dto.AdsResponse;
import ru.skypro.homework.dto.CreateOrUpdateAdRequest;
import ru.skypro.homework.dto.ExtendedAdResponse;

@RestController
@RequestMapping("/ads")
public class AdController {
    @Operation(summary = "Получение всех объявлений")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Список объявлений",
            content = @Content(schema = @Schema(implementation = AdsResponse.class))) })
    @GetMapping
    public ResponseEntity<AdsResponse> getAllAds() {
        return ResponseEntity.ok(new AdsResponse());
    }

    @Operation(summary = "Добавление объявления")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Объявление создано",
            content = @Content(schema = @Schema(implementation = AdResponse.class))),
            @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content) })
    @PostMapping
    public ResponseEntity<AdResponse> addAd(
            @RequestPart("properties") CreateOrUpdateAdRequest request,
            @RequestPart("image") String image) {
        return ResponseEntity.status(201).body(new AdResponse());
    }

    @Operation(summary = "Получение информации об объявлении")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Данные объявления",
            content = @Content(schema = @Schema(implementation = ExtendedAdResponse.class))),
            @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Объявление не найдено", content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAdResponse> getAds(@PathVariable Integer id) {
        return ResponseEntity.ok(new ExtendedAdResponse());
    }

    @Operation(summary = "Удаление объявления")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Объявление удалено"),
            @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен", content = @Content),
            @ApiResponse(responseCode = "404", description = "Объявление не найдено", content = @Content) })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeAd(@PathVariable Integer id) {
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Обновление информации об объявлении")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Объявление обновлено",
            content = @Content(schema = @Schema(implementation = AdResponse.class))),
            @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен", content = @Content),
            @ApiResponse(responseCode = "404", description = "Объявление не найдено", content = @Content) })
    @PatchMapping("/{id}")
    public ResponseEntity<AdResponse> updateAds(@PathVariable Integer id, @RequestBody CreateOrUpdateAdRequest request) {
        return ResponseEntity.ok(new AdResponse());
    }

    @Operation(summary = "Получение объявлений авторизованного пользователя")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Список моих объявлений",
            content = @Content(schema = @Schema(implementation = AdsResponse.class))),
            @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content) })
    @GetMapping("/me")
    public ResponseEntity<AdsResponse> getAdsMe() {
        return ResponseEntity.ok(new AdsResponse());
    }

    @Operation(summary = "Обновление картинки объявления")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Картинка обновлена"),
            @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен", content = @Content),
            @ApiResponse(responseCode = "404", description = "Объявление не найдено", content = @Content) })
    @PatchMapping("/{id}/image")
    public ResponseEntity<?> updateImage(@PathVariable Integer id, @RequestPart("image") String image) {
        return ResponseEntity.ok().build();
    }
}
