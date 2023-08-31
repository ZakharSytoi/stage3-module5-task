package com.mjc.school.service.mappers;

import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.model.impl.CommentModel;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {
    @Autowired
    NewsRepository newsRepository;
    public abstract List<CommentDtoResponse> commentModelListToCommentDtoList(List<CommentModel> modelList);

    public abstract CommentDtoResponse commentModelToCommentDtoResponse(CommentModel model);

    @Mapping(target = "news", expression = "java(newsRepository.getReference(requestDto.newsId()))")
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastUpdatedDate", ignore = true)
    public abstract CommentModel commentDotRequestToCommentModel(CommentDtoRequest requestDto);
}
