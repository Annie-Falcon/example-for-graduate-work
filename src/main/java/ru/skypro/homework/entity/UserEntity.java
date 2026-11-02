package ru.skypro.homework.entity;

import lombok.*;
import ru.skypro.homework.dto.Role;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Entity
@Table(name = "users") // пользователи
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // id пользователя

    @Column(unique = true, nullable = false)
    @Email
    @NotBlank
    private String email; // логин пользователя

    @Column(nullable = false)
    @NotBlank
    private String password; // пароль пользователя

    @Column(name = "first_name", nullable = false)
    @NotBlank
    private String firstName; // имя пользователя

    @Column(name = "last_name", nullable = false)
    @NotBlank
    private String lastName; // фамилия пользователя

    @Column(nullable = false)
    @NotBlank
    @Pattern(regexp = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}")
    private String phone; // телефон пользователя

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role; // роль пользователя

    @Column
    private String image; // ссылка на аватар пользователя

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<AdEntity> ads; // список объявлений пользователя

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CommentEntity> comments; // список комментариев пользователя
}
