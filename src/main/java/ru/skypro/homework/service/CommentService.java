package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.CommentResponse;
import ru.skypro.homework.dto.CommentsResponse;
import ru.skypro.homework.dto.CreateOrUpdateCommentRequest;

import java.util.Optional;

public interface CommentService {

    CommentsResponse getAllComments(Integer adId);

    CommentResponse createComment(Integer adId,
                                  CreateOrUpdateCommentRequest request,
                                  Authentication auth);

    boolean delete(Integer id);

    Optional<CommentResponse> update(Integer id, CreateOrUpdateCommentRequest textComment);
}
