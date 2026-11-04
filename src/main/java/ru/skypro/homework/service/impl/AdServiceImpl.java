package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.AdResponse;
import ru.skypro.homework.dto.AdsResponse;
import ru.skypro.homework.dto.CreateOrUpdateAdRequest;
import ru.skypro.homework.dto.ExtendedAdResponse;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdService;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final AdMapper adMapper;
    private final UserRepository userRepository;

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

    @PreAuthorize("#ad.author.email == authentication.name or hasAuthority('ROLE_ADMIN')")
    @Transactional
    @Override
    public Optional<AdResponse> update(Integer id, CreateOrUpdateAdRequest adRequest) {
        return adRepository.findById(id).map(ad -> {
            adMapper.updateAdFromDto(adRequest, ad);  // Обновляем поля сущности
            return adMapper.toDto(adRepository.save(ad));
        });
    }

    @PreAuthorize("#ad.author.email == authentication.name or hasAuthority('ROLE_ADMIN')")
    @Transactional
    @Override
    public void delete(Integer id) {
        var optionalAd = adRepository.findById(id);
        if (optionalAd.isEmpty()) {
            throw new IllegalArgumentException("Объявления не существует");
        }
        adRepository.deleteById(id);
    }

    @Override
    @Transactional
    public boolean setImage(Integer id, String image) {
        return adRepository.findById(id).map(ad -> {
            // Здесь должна быть логика сохранения файла и обновления ссылки
            //ad.setImage("data:image/jpeg;base64," + Base64.getEncoder().encodeToString(imageData));
            adRepository.save(ad);
            return true;
        }).orElse(false);
    }
}
