package com.mjc.school.service.mappers;

import com.mjc.school.repository.model.impl.AuthorModel;
import com.mjc.school.repository.model.impl.NewsModel;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-30T12:26:06+0200",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.2.jar, environment: Java 17.0.6 (Oracle Corporation)"
)
@Component
public class NewsMapperImpl extends NewsMapper {

    @Override
    public List<NewsDtoResponse> newsModelListToNewsDtoResponseList(List<NewsModel> newsModelList) {
        if ( newsModelList == null ) {
            return null;
        }

        List<NewsDtoResponse> list = new ArrayList<NewsDtoResponse>( newsModelList.size() );
        for ( NewsModel newsModel : newsModelList ) {
            list.add( newsModelToNewsDtoResponse( newsModel ) );
        }

        return list;
    }

    @Override
    public NewsDtoResponse newsModelToNewsDtoResponse(NewsModel newsModel) {
        if ( newsModel == null ) {
            return null;
        }

        Long authorId = null;
        Long id = null;
        String title = null;
        String content = null;
        LocalDateTime createdDate = null;
        LocalDateTime lastUpdatedDate = null;

        authorId = newsModelAuthorId( newsModel );
        id = newsModel.getId();
        title = newsModel.getTitle();
        content = newsModel.getContent();
        createdDate = newsModel.getCreatedDate();
        lastUpdatedDate = newsModel.getLastUpdatedDate();

        NewsDtoResponse newsDtoResponse = new NewsDtoResponse( id, title, content, createdDate, lastUpdatedDate, authorId );

        return newsDtoResponse;
    }

    @Override
    public NewsModel newsDtoRequestToNewsModel(NewsDtoRequest newsDtoRequest) {
        if ( newsDtoRequest == null ) {
            return null;
        }

        NewsModel newsModel = new NewsModel();

        newsModel.setId( newsDtoRequest.id() );
        newsModel.setContent( newsDtoRequest.content() );
        newsModel.setTitle( newsDtoRequest.title() );

        newsModel.setAuthor( authorRepository.getReference(newsDtoRequest.authorId()) );
        newsModel.setTags( newsDtoRequest.tagIds().stream().map(id -> tagRepository.getReference(id)).toList() );

        return newsModel;
    }

    private Long newsModelAuthorId(NewsModel newsModel) {
        if ( newsModel == null ) {
            return null;
        }
        AuthorModel author = newsModel.getAuthor();
        if ( author == null ) {
            return null;
        }
        Long id = author.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
