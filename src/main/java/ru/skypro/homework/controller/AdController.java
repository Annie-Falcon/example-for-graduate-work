package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdResponse;
import ru.skypro.homework.dto.AdsResponse;
import ru.skypro.homework.dto.CreateOrUpdateAdRequest;
import ru.skypro.homework.dto.ExtendedAdResponse;
import ru.skypro.homework.service.AdService;

import java.io.IOException;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdController {
    private final AdService adService;

    @Operation(summary = "Получение всех объявлений")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Список объявлений",
            content = @Content(schema = @Schema(implementation = AdsResponse.class)))})
    @GetMapping
    public ResponseEntity<AdsResponse> getAllAds() {
        return ResponseEntity.ok(adService.getAll());
    }

    @Operation(summary = "Получение объявлений авторизованного пользователя")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Список моих объявлений",
            content = @Content(schema = @Schema(implementation = AdsResponse.class))),
            @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content)})
    @GetMapping("/me")
    public ResponseEntity<AdsResponse> getAdsMe(Authentication auth) {

        return ResponseEntity.ok(adService.getByUserId(auth));
    }

    @Operation(summary = "Получение информации об объявлении")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Данные объявления",
            content = @Content(schema = @Schema(implementation = ExtendedAdResponse.class))),
            @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Объявление не найдено", content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAdResponse> getAds(@PathVariable Integer id) {
        return adService.getById(id)
                .map(ResponseEntity::ok) // Если объявление есть — вернём его
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Добавление объявления")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Объявление создано",
            content = @Content(schema = @Schema(implementation = AdResponse.class))),
            @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content)})
    @PostMapping
    public ResponseEntity<AdResponse> addAd(
            @RequestPart("properties") CreateOrUpdateAdRequest request,
            Authentication auth) {
        var response = adService.create(request, auth);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Удаление объявления")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Объявление удалено"),
            @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен", content = @Content),
            @ApiResponse(responseCode = "404", description = "Объявление не найдено", content = @Content)})
    @PreAuthorize("#ad.author.email == authentication.name or hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAd(@PathVariable Integer id) {
        try {
            adService.delete(id); // Здесь может быть выброшено исключение
            return ResponseEntity.noContent().build(); // 204 при успехе
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    @Operation(summary = "Обновление информации об объявлении")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Объявление обновлено",
            content = @Content(schema = @Schema(implementation = AdResponse.class))),
            @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен", content = @Content),
            @ApiResponse(responseCode = "404", description = "Объявление не найдено", content = @Content)})
    @PreAuthorize("#ad.author.email == authentication.name or hasAuthority('ROLE_ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<AdResponse> updateAds(@PathVariable Integer id, @RequestBody CreateOrUpdateAdRequest request) {
        return adService.update(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Обновление картинки объявления")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Картинка обновлена"),
            @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен", content = @Content),
            @ApiResponse(responseCode = "404", description = "Объявление не найдено", content = @Content)})
    @PreAuthorize("#ad.author.email == authentication.name or hasAuthority('ROLE_ADMIN')")
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateImage(@PathVariable Integer id,
                                            @RequestPart("image") MultipartFile image) throws IOException {
        adService.setImage(id, image);
        return ResponseEntity.ok().build();
    }
}
