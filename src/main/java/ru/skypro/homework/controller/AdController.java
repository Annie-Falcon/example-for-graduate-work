package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;

@RestController
@RequestMapping("/ads")
public class AdController {

    @Operation(summary = "Получение всех объявлений")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Список объявлений",
            content = @Content(schema = @Schema(implementation = Ads.class))) })
    @GetMapping
    public ResponseEntity<Ads> getAllAds() {
        return ResponseEntity.ok(new Ads());
    }

    @Operation(summary = "Добавление объявления")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Объявление создано",
            content = @Content(schema = @Schema(implementation = Ad.class))),
            @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content) })
    @PostMapping
    public ResponseEntity<Ad> addAd(
            @RequestPart("properties") CreateOrUpdateAd request,
            @RequestPart("image") String image) {
        return ResponseEntity.status(201).body(new Ad());
    }

    @Operation(summary = "Получение информации об объявлении")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Данные объявления",
            content = @Content(schema = @Schema(implementation = ExtendedAd.class))),
            @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Объявление не найдено", content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAd> getAds(@PathVariable Integer id) {
        return ResponseEntity.ok(new ExtendedAd());
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
            content = @Content(schema = @Schema(implementation = Ad.class))),
            @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен", content = @Content),
            @ApiResponse(responseCode = "404", description = "Объявление не найдено", content = @Content) })
    @PatchMapping("/{id}")
    public ResponseEntity<Ad> updateAds(@PathVariable Integer id, @RequestBody CreateOrUpdateAd request) {
        return ResponseEntity.ok(new Ad());
    }

    @Operation(summary = "Получение объявлений авторизованного пользователя")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Список моих объявлений",
            content = @Content(schema = @Schema(implementation = Ads.class))),
            @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content) })
    @GetMapping("/me")
    public ResponseEntity<Ads> getAdsMe() {
        return ResponseEntity.ok(new Ads());
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
