package ru.skypro.homework.service;


import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.CreateOrUpdateAdRequest;
import ru.skypro.homework.dto.AdResponse;
import ru.skypro.homework.dto.AdsResponse;
import ru.skypro.homework.dto.ExtendedAdResponse;

import java.io.IOException;
import java.util.Optional;

public interface AdService {
    AdsResponse getAll();

    Optional<ExtendedAdResponse> getById(Integer id);

    AdResponse create(CreateOrUpdateAdRequest ad, Authentication auth);

    Optional<AdResponse> update(Integer id, CreateOrUpdateAdRequest ad);

    void delete(Integer id);

    AdsResponse getByUserId(Authentication auth);

    void setImage(Integer id, MultipartFile file) throws IOException;
}