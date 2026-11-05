package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.CommentResponse;
import ru.skypro.homework.dto.CommentsResponse;
import ru.skypro.homework.dto.CreateOrUpdateCommentRequest;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.CommentService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final AdRepository adRepo;
    private final CommentRepository commentRepo;
    private final CommentMapper mapper;

    @Override
    public CommentsResponse getAllComments(Integer adId) { // GET /ads/{adId}/comments
        var comments = commentRepo.findAllByAdPk(adId);
        return new CommentsResponse(comments.size(), mapper.toDtoList(comments));
    }

    @Transactional
    @Override
    public CommentResponse createComment(Integer adId,
                                         CreateOrUpdateCommentRequest request,
                                         Authentication auth) { // POST /ads/{adId}/comments
        var authorEmail = auth.getName();
        var optionalAd = adRepo.findById(adId);

        if (optionalAd.isEmpty()) {
            throw new IllegalArgumentException("Объявление не найдено");
        }

        var entity = CommentEntity.builder()
                .text(request.getText())
                .author(UserEntity.builder().email(authorEmail).build()) // Заглушка автора по email
                .ad(optionalAd.get()) // Связываем с объявлением
                .build();

        return mapper.toDto(commentRepo.save(entity)); // Сохраняем и маппим обратно в DTO
    }

    @Transactional
    @Override
    public boolean delete(Integer id) {
        if (commentRepo.existsById(id)) {
            commentRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public Optional<CommentResponse> update(Integer id, CreateOrUpdateCommentRequest textComment) { // PATCH /ads/{adId}/comments/{commentId}
        return commentRepo.findById(id).map(ad -> {
            mapper.updateCommentFromDto(textComment, ad);  // Обновляем поля сущности
            return mapper.toDto(commentRepo.save(ad));
        });
    }
}
