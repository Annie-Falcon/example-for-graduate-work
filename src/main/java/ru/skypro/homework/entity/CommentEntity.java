package ru.skypro.homework.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments") // комментарии
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk; // id комментария

    @Column(nullable = false, length = 64)
    @NotBlank
    private String text; // текст комментария

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private UserEntity author; // id автора комментария

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ad_id", nullable = false)
    private AdEntity ad; // id объявления
}
