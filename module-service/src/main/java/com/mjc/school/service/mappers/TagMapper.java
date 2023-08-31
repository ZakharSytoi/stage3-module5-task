package com.mjc.school.service.mappers;

import com.mjc.school.repository.model.impl.TagModel;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    List<TagDtoResponse> tagModelListToTagDtoResponseList(List<TagModel> tagModelList);

    TagDtoResponse tagModelToTagDtoResponse(TagModel tagModel);

    @Mapping(target = "news", ignore = true)
    TagModel tagModelDtoToTagModel(TagDtoRequest TagDtoRequest);

}
