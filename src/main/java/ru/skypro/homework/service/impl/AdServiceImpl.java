package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdResponse;
import ru.skypro.homework.dto.AdsResponse;
import ru.skypro.homework.dto.CreateOrUpdateAdRequest;
import ru.skypro.homework.dto.ExtendedAdResponse;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.utils.ImageStorageService;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final AdMapper adMapper;
    private final UserRepository userRepository;
    private final ImageStorageService imageStorage;

    @Override
    public AdsResponse getAll() { // GET /ads
        List<AdEntity> ads = adRepository.findAll();
        return new AdsResponse(ads.size(), adMapper.toDtoList(ads));
    }

    @Override
    public AdsResponse getByUserId(Authentication auth) { //GET /ads /me
        var author = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new IllegalStateException("Пользователь не найден"));

        List<AdEntity> ads = adRepository.findAllByAuthorId(author.getId());
        return new AdsResponse(ads.size(), adMapper.toDtoList(ads));
    }

    @Override
    public Optional<ExtendedAdResponse> getById(Integer id) { // GET /ads/{id}
        return adRepository.findById(id).map(adMapper::toExtendedDto);
    }

    @Transactional
    @Override
    public AdResponse create(CreateOrUpdateAdRequest adRequest, Authentication auth) {
        var author = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new IllegalStateException("Пользователь не найден"));
        AdEntity ad = adMapper.toEntity(adRequest);  // Маппинг из DTO в Entity
        ad.setAuthor(author);
        return adMapper.toDto(adRepository.save(ad));
    }

    @Transactional
    @Override
    public Optional<AdResponse> update(Integer id, CreateOrUpdateAdRequest adRequest) {
        return adRepository.findById(id).map(ad -> {
            adMapper.updateAdFromDto(adRequest, ad);  // Обновляем поля сущности
            return adMapper.toDto(adRepository.save(ad));
        });
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        var optionalAd = adRepository.findById(id);
        if (optionalAd.isEmpty()) {
            throw new IllegalArgumentException("Объявления не существует");
        }
        adRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void setImage(Integer id, MultipartFile file) throws IOException {
        AdEntity ad = adRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Объявление не найдено"));

        // сохранить файл на диск и получить путь
        String savedPath = imageStorage.save(file, "ADS");
        ad.setImage(savedPath);
        adRepository.save(ad);
    }
}
