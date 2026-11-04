package ru.skypro.homework.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.NewPasswordRequest;
import ru.skypro.homework.dto.UpdateUserRequest;
import ru.skypro.homework.dto.UserResponse;
import ru.skypro.homework.entity.UserEntity;

public interface UserService {
    UserResponse getCurrentUser(Authentication auth);

    void update(UpdateUserRequest dto, Authentication auth);

    public ResponseEntity<?> updateAvatar(String image, Authentication auth);

    public boolean changePassword(NewPasswordRequest req, Authentication auth);
}
