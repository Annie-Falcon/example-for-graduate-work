package ru.skypro.homework.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.CreateOrUpdateAdRequest;
import ru.skypro.homework.dto.AdResponse;
import ru.skypro.homework.dto.ExtendedAdResponse;
import ru.skypro.homework.entity.AdEntity;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdMapper {

    AdMapper INSTANCE = Mappers.getMapper(AdMapper.class);

    AdResponse toDto(AdEntity ad);

    ExtendedAdResponse toExtendedDto(AdEntity ad);

    AdEntity toEntity(CreateOrUpdateAdRequest request);

    void updateAdFromDto(CreateOrUpdateAdRequest request, @MappingTarget AdEntity ad);

    List<AdResponse> toDtoList(List<AdEntity> ads);

}