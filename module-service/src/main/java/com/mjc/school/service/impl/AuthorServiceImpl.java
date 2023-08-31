package com.mjc.school.service.impl;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ValidatorException;
import com.mjc.school.service.mappers.AuthorMapper;
import com.mjc.school.service.paging.PagingUtil;
import com.mjc.school.service.validation.validators.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mjc.school.service.exceptions.ServiceErrorCode.AUTHOR_DOES_NOT_EXIST_FOR_NEWS_ID;
import static com.mjc.school.service.exceptions.ServiceErrorCode.AUTHOR_ID_DOES_NOT_EXIST;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper = AuthorMapper.INSTANCE;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    @Transactional
    public AuthorDtoResponse readByNewsId(Long newsId) {
        return authorMapper.authorModelToAuthorDtoResponse(
                authorRepository.readByNewsId(newsId).orElseThrow(() ->
                        new NotFoundException(String.format(AUTHOR_DOES_NOT_EXIST_FOR_NEWS_ID.getMessage(), newsId))
                )
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuthorDtoResponse> readAll(Pageable pageRequest) {
        List<AuthorDtoResponse> authorList = authorMapper.authorModelListToAuthorDtoResponseList(authorRepository.readAll());
        return PagingUtil.createPageFromList(pageRequest, authorList);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorDtoResponse readById(Long id) {
        return authorMapper.authorModelToAuthorDtoResponse(
                authorRepository
                        .readById(id)
                        .orElseThrow(() -> new ValidatorException(String.format(
                                AUTHOR_ID_DOES_NOT_EXIST.getMessage(),
                                id
                        ))));
    }

    @Override
    @Transactional
    public AuthorDtoResponse create(AuthorDtoRequest createRequest) {
        RequestValidator.validateDTORequestForCreate(createRequest);
        return authorMapper.authorModelToAuthorDtoResponse(authorRepository
                .create(authorMapper
                        .authorModelDtoToAuthorModel(createRequest)));
    }

    @Override
    @Transactional
    public AuthorDtoResponse update(AuthorDtoRequest updateRequest) {
        RequestValidator.validateDTORequestForUpdate(updateRequest);
        if (authorRepository.existById(updateRequest.id())) {
            return authorMapper
                    .authorModelToAuthorDtoResponse(authorRepository
                            .update(authorMapper.authorModelDtoToAuthorModel(updateRequest)));
        } else {
            throw new NotFoundException(String.format(AUTHOR_ID_DOES_NOT_EXIST.getMessage(), updateRequest.id()));
        }

    }

    @Override
    @Transactional
    public Boolean deleteById(Long id) {
        if (authorRepository.existById(id)) {
            return authorRepository.deleteById(id);
        } else {
            throw new NotFoundException(String.format(AUTHOR_ID_DOES_NOT_EXIST.getMessage(), id));
        }
    }
}
