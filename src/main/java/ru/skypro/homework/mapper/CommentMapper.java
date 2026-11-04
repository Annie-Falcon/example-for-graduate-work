package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.CommentResponse;
import ru.skypro.homework.dto.CreateOrUpdateCommentRequest;
import ru.skypro.homework.entity.CommentEntity;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    CommentResponse toDto(CommentEntity comment);

    void updateCommentFromDto(CreateOrUpdateCommentRequest request, @MappingTarget CommentEntity comment);

    List<CommentResponse> toDtoList(List<CommentEntity> comments);
}
