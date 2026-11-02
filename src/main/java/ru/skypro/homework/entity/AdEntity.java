package ru.skypro.homework.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "ads") // объявления
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;  // id объявления

    @Column(nullable = false)
    @NotBlank
    private String title; // заголовок объявления

    @Column(nullable = false)
    @NotNull
    private Integer price; // цена объявления

    @Column(nullable = false, length = 64)
    @NotBlank
    private String description; // описание объявления

    @Column
    private String image; // ссылка на картинку объявления

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private UserEntity author;  // id автора объявления

    @OneToMany(mappedBy = "ad", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CommentEntity> comments; // список комментариев к объявлению
}
