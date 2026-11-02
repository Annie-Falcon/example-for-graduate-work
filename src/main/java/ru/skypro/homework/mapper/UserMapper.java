package ru.skypro.homework.mapper;

import org.mapstruct.*;
import ru.skypro.homework.dto.UpdateUserRequest;
import ru.skypro.homework.dto.UserResponse;
import ru.skypro.homework.entity.UserEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserResponse toDto(UserEntity user);

    void updateUserFromDto(UpdateUserRequest request, @MappingTarget UserEntity user);

    UserEntity fromDto(UpdateUserRequest request);
}
