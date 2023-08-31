package com.mjc.school.service.mappers;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.TagRepository;
import com.mjc.school.repository.model.impl.NewsModel;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper (componentModel = "spring")
public abstract class NewsMapper {
    @Autowired
    protected AuthorRepository authorRepository;
    @Autowired
    protected TagRepository tagRepository;


    public abstract List<NewsDtoResponse> newsModelListToNewsDtoResponseList(List<NewsModel> newsModelList);

    @Mappings(value = {@Mapping(target = "authorId", source = "newsModel.author.id")})
    public abstract NewsDtoResponse newsModelToNewsDtoResponse(NewsModel newsModel);

    @Mappings(value = {
            @Mapping(target = "createdDate", ignore = true),
            @Mapping(target = "lastUpdatedDate", ignore = true),
            @Mapping(target = "author", expression = "java(authorRepository.getReference(newsDtoRequest.authorId()))"),
            @Mapping(target = "tags", expression = "java(newsDtoRequest.tagIds().stream().map(id -> tagRepository.getReference(id)).toList())"),
    })
    public abstract NewsModel newsDtoRequestToNewsModel(NewsDtoRequest newsDtoRequest);
}
