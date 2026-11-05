package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.NewPasswordRequest;
import ru.skypro.homework.dto.UpdateUserRequest;
import ru.skypro.homework.dto.UserResponse;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper;

    @Override
    public UserResponse getCurrentUser(Authentication auth) { //GET /users/me
        String email = auth.getName(); // В Basic Auth логин — это email
        return userRepo.findByEmail(email).map(mapper::toDto).orElse(null);
    }

    @PreAuthorize("#user.email == authentication.name or hasAuthority('ROLE_ADMIN')")
    @Transactional
    @Override
    public void update(UpdateUserRequest dto, Authentication auth) { // PATCH /users/me
        UserEntity user = userRepo.findByEmail(auth.getName()).orElseThrow(NoSuchElementException::new);

        mapper.updateUserFromDto(dto, user); // Маппер обновляет только те поля, которые пришли из запроса
        userRepo.save(user);
    }

    @PreAuthorize("#user.email == authentication.name or hasAuthority('ROLE_ADMIN')")
    @Override
    public ResponseEntity<?> updateAvatar(String image, Authentication auth) { // PATCH /users/me/image
        UserEntity user = userRepo.findByEmail(auth.getName()).orElseThrow(NoSuchElementException::new);
        // Здесь должна быть логика сохранения файла на диск или в облако
        return ResponseEntity.ok().build();
    }

    @Transactional
    @Override
    public boolean changePassword(NewPasswordRequest req, Authentication auth) { // POST /users/set_password
        var currentUser = userRepo.findByEmail(auth.getName())
                .orElseThrow(() -> new IllegalStateException("Пользователь не найден"));

        // Критерий: проверка совпадения oldPassword с текущим хешем
        if (!passwordEncoder.matches(req.getCurrentPassword(), currentUser.getPassword())) {
            throw new BadCredentialsException("Неверный текущий пароль");
        }

        // Шифруем новый пароль перед сохранением
        currentUser.setPassword(passwordEncoder.encode(req.getNewPassword()));
        userRepo.save(currentUser);
        return true;
    }
}