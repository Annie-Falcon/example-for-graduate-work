package ru.skypro.homework.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    //private final UserDetailsManager manager;
    private final PasswordEncoder encoder;
    private final UserRepository userRepo;

    public AuthServiceImpl(//UserDetailsManager manager,
                           PasswordEncoder passwordEncoder,
                           UserRepository userRepo) {
        //this.manager = manager;
        this.encoder = passwordEncoder;
        this.userRepo = userRepo;
    }

    @Override
    public boolean login(String userName, String password) {
        /*if (!manager.userExists(userName)) {
            return false;
        }
        UserDetails userDetails = manager.loadUserByUsername(userName);
        return encoder.matches(password, userDetails.getPassword());*/
        try {
            var user = userRepo.findByEmail(userName)
                    .orElseThrow(() -> new UsernameNotFoundException("Пользователя не существует"));

            return encoder.matches(password, user.getPassword());
        } catch (UsernameNotFoundException e) {
            return false;
        }
    }

    @Override
    public boolean register(Register register) {
        /*if (manager.userExists(register.getUsername())) {
            return false;
        }
        manager.createUser(
                User.builder()
                        .passwordEncoder(this.encoder::encode)
                        .password(register.getPassword())
                        .username(register.getUsername())
                        .roles(register.getRole().name())
                        .build());
        */
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
