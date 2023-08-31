package com.mjc.school.service.mappers;

import com.mjc.school.repository.model.impl.AuthorModel;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
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
public class AuthorMapperImpl implements AuthorMapper {

    @Override
    public List<AuthorDtoResponse> authorModelListToAuthorDtoResponseList(List<AuthorModel> authorModelList) {
        if ( authorModelList == null ) {
            return null;
        }

        List<AuthorDtoResponse> list = new ArrayList<AuthorDtoResponse>( authorModelList.size() );
        for ( AuthorModel authorModel : authorModelList ) {
            list.add( authorModelToAuthorDtoResponse( authorModel ) );
        }

        return list;
    }

    @Override
    public AuthorDtoResponse authorModelToAuthorDtoResponse(AuthorModel authorModel) {
        if ( authorModel == null ) {
            return null;
        }

        Long id = null;
        String name = null;

        id = authorModel.getId();
        name = authorModel.getName();

        AuthorDtoResponse authorDtoResponse = new AuthorDtoResponse( id, name );

        return authorDtoResponse;
    }

    @Override
    public AuthorModel authorModelDtoToAuthorModel(AuthorDtoRequest authorDtoRequest) {
        if ( authorDtoRequest == null ) {
            return null;
        }

        AuthorModel authorModel = new AuthorModel();

        authorModel.setId( authorDtoRequest.id() );
        authorModel.setName( authorDtoRequest.name() );

        return authorModel;
    }
}
