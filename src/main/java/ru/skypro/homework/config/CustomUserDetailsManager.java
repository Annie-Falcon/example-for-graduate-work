package ru.skypro.homework.config;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.UserRepository;

import java.util.Collections;

@Service
public class CustomUserDetailsManager implements UserDetailsManager {

    private final UserRepository userRepository;

    public CustomUserDetailsManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private UserDetails toSpringSecurityUser(UserEntity entity) {
        String role = "ROLE_" + entity.getRole().name(); // Добавляем ROLE_

        return new User(
                entity.getEmail(), // username
                entity.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(role))
        );
    }

    //Используется Spring Security для поиска пользователя по логину.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(this::toSpringSecurityUser)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + email));
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.existsByEmail(username);
    }

    // Создание нового пользователя. Этот метод обычно вызывается из контроллера регистрации /register
    // Но в этом приложении он не используется. Нужен только для следования интерфейсу
    @Override
    public void createUser(UserDetails user) {
        Object o = null;
    }

    // Обновление существующего пользователя.
    // Но в этом приложении он не используется. Нужен только для следования интерфейсу
    @Override
    public void updateUser(UserDetails user) {
        Object o = null;
    }

    // Удаление пользователя по его email.
    // Но в этом приложении он не используется. Нужен только для следования интерфейсу
    @Override
    public void deleteUser(String email) {
        Object o = null;
    }

    /**
     * Сбрасывает пароль текущего авторизованного пользователя.
     * Вызывает исключение, если пользователь не вошёл в систему.
     * Но в этом приложении он не используется. Нужен только для следования интерфейсу
     */
    @Override
    public void changePassword(String rawOldPassword, String rawNewPassword) {
        Object o = null;
    }
}
