package com.mjc.school.service.mappers;

import com.mjc.school.repository.model.impl.CommentModel;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-31T12:45:49+0200",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.2.jar, environment: Java 17.0.6 (Oracle Corporation)"
)
@Component
public class CommentMapperImpl extends CommentMapper {

    @Override
    public List<CommentDtoResponse> commentModelListToCommentDtoList(List<CommentModel> modelList) {
        if ( modelList == null ) {
            return null;
        }

        List<CommentDtoResponse> list = new ArrayList<CommentDtoResponse>( modelList.size() );
        for ( CommentModel commentModel : modelList ) {
            list.add( commentModelToCommentDtoResponse( commentModel ) );
        }

        return list;
    }

    @Override
    public CommentDtoResponse commentModelToCommentDtoResponse(CommentModel model) {
        if ( model == null ) {
            return null;
        }

        Long id = null;
        String content = null;
        LocalDateTime createdDate = null;
        LocalDateTime lastUpdatedDate = null;

        id = model.getId();
        content = model.getContent();
        createdDate = model.getCreatedDate();
        lastUpdatedDate = model.getLastUpdatedDate();

        CommentDtoResponse commentDtoResponse = new CommentDtoResponse( id, content, createdDate, lastUpdatedDate );

        return commentDtoResponse;
    }

    @Override
    public CommentModel commentDotRequestToCommentModel(CommentDtoRequest requestDto) {
        if ( requestDto == null ) {
            return null;
        }

        CommentModel commentModel = new CommentModel();

        commentModel.setId( requestDto.id() );
        commentModel.setContent( requestDto.content() );

        commentModel.setNews( newsRepository.getReference(requestDto.newsId()) );

        return commentModel;
    }
}
