package com.mjc.school.service.impl;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.TagRepository;
import com.mjc.school.repository.query.NewsSearchQueryParams;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.mappers.NewsMapper;
import com.mjc.school.service.paging.PagingUtil;
import com.mjc.school.service.query.NewsQueryParams;
import com.mjc.school.service.validation.validators.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mjc.school.service.exceptions.ServiceErrorCode.*;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final AuthorRepository authorRepository;
    private final TagRepository tagRepository;
    private final NewsMapper newsMapper;

    @Autowired
    public NewsServiceImpl(
            NewsRepository newsRepository,
            AuthorRepository authorRepository,
            TagRepository tagRepository,
            NewsMapper newsMapper
    ) {
        this.newsRepository = newsRepository;
        this.authorRepository = authorRepository;
        this.tagRepository = tagRepository;
        this.newsMapper = newsMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NewsDtoResponse> readAll(Pageable pageRequest) {
        List<NewsDtoResponse> newsList = newsMapper.newsModelListToNewsDtoResponseList(newsRepository.readAll());
        return PagingUtil.createPageFromList(pageRequest, newsList);
    }

    @Override
    @Transactional(readOnly = true)
    public NewsDtoResponse readById(Long id) {
        return newsMapper.newsModelToNewsDtoResponse(
                newsRepository.readById(id).orElseThrow(() -> new NotFoundException(
                        String.format(
                                NEWS_ID_DOES_NOT_EXIST.getMessage(),
                                id
                        ))
                ));
    }

    @Override
    @Transactional
    public NewsDtoResponse create(NewsDtoRequest createRequest) {
        RequestValidator.validateDTORequestForCreate(createRequest);
        if (!authorRepository.existById(createRequest.authorId())) {
            throw new NotFoundException(String.format(AUTHOR_ID_DOES_NOT_EXIST.getMessage(), createRequest.authorId()));
        }

        createRequest.tagIds().forEach((tagId) -> {
            if (!tagRepository.existById(tagId))
                throw new NotFoundException(String.format(TAG_ID_DOES_NOT_EXIST.getMessage(), tagId));
        });

        return newsMapper.newsModelToNewsDtoResponse(
                newsRepository
                        .create(newsMapper
                                .newsDtoRequestToNewsModel(createRequest))
        );
    }

    @Override
    @Transactional
    public NewsDtoResponse update(NewsDtoRequest updateRequest) {
        RequestValidator.validateDTORequestForUpdate(updateRequest);
        if (!authorRepository.existById(updateRequest.authorId())) {
            throw new NotFoundException(String.format(AUTHOR_ID_DOES_NOT_EXIST.getMessage(), updateRequest.authorId()));
        }

        if (!newsRepository.existById(updateRequest.id())) {
            throw new NotFoundException(String.format(NEWS_ID_DOES_NOT_EXIST.getMessage(), updateRequest.id()));
        }

        updateRequest.tagIds().forEach((tagId) -> {
            if (!tagRepository.existById(tagId))
                throw new NotFoundException(String.format(TAG_ID_DOES_NOT_EXIST.getMessage(), tagId));
        });

        return newsMapper.newsModelToNewsDtoResponse(
                newsRepository
                        .update(newsMapper
                                .newsDtoRequestToNewsModel(updateRequest))
        );
    }

    @Override
    @Transactional
    public Boolean deleteById(Long id) {
        if (newsRepository.existById(id)) {
            return newsRepository.deleteById(id);
        } else {
            throw new NotFoundException(String.format(NEWS_ID_DOES_NOT_EXIST.getMessage(), id));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<NewsDtoResponse> readByQueryParams(NewsQueryParams queryParams) {
        NewsSearchQueryParams searchQueryParams = new NewsSearchQueryParams(
                queryParams.tagNames(),
                queryParams.tagIds(),
                queryParams.authorName(),
                queryParams.title(),
                queryParams.content());
        return newsMapper.newsModelListToNewsDtoResponseList(newsRepository.readBySearchParams(searchQueryParams));
    }
}
