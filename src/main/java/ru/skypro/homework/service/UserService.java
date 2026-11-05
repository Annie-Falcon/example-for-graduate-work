package ru.skypro.homework.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordRequest;
import ru.skypro.homework.dto.UpdateUserRequest;
import ru.skypro.homework.dto.UserResponse;
import ru.skypro.homework.entity.UserEntity;

import java.io.IOException;

public interface UserService {
    UserResponse getCurrentUser(Authentication auth);

    void update(UpdateUserRequest dto, Authentication auth);

    public ResponseEntity<?> updateAvatar(MultipartFile file, Authentication auth) throws IOException;

    public boolean changePassword(NewPasswordRequest req, Authentication auth);
}
