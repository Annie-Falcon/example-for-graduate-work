package ru.skypro.homework.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final UserRepository userRepo;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           PasswordEncoder passwordEncoder,
                           UserRepository userRepo) {
        this.authenticationManager = authenticationManager;
        this.encoder = passwordEncoder;
        this.userRepo = userRepo;
    }

    @Override
    public boolean login(String userName, String password) {
        try {
            //Создаем токен из данных запроса
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(userName, password);
            //Передаем его менеджеру.
            // Если пароль неверный или пользователь не найден — выбросится BadCredentialsException
            Authentication authentication = authenticationManager.authenticate(authInputToken);
            return true;
        } catch (BadCredentialsException e) {
            return false;
        }
    }

    @Override
    public boolean register(Register register) {
        if (userRepo.existsByEmail(register.getUsername())) return false;

        UserEntity user = UserEntity.builder()
                .email(register.getUsername())                    // Логин - email
                .password(encoder.encode(register.getPassword())) // Шифруем пароль
                .firstName(register.getFirstName())
                .lastName(register.getLastName())
                .phone(register.getPhone())
                .role(register.getRole())
                .build();

        userRepo.save(user);

        return true;
    }
}
