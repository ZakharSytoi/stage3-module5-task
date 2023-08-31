package com.mjc.school.service.impl;


import com.mjc.school.repository.TagRepository;
import com.mjc.school.service.TagService;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ValidatorException;
import com.mjc.school.service.mappers.TagMapper;
import com.mjc.school.service.paging.PagingUtil;
import com.mjc.school.service.validation.validators.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mjc.school.service.exceptions.ServiceErrorCode.TAG_ID_DOES_NOT_EXIST;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    private final TagMapper tagMapper = TagMapper.INSTANCE;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TagDtoResponse> readAll(Pageable pageRequest) {
        List<TagDtoResponse> tagList = tagMapper.tagModelListToTagDtoResponseList(tagRepository.readAll());
        return PagingUtil.createPageFromList(pageRequest, tagList);
    }

    @Override
    @Transactional(readOnly = true)
    public TagDtoResponse readById(Long id) {
        return tagMapper
                .tagModelToTagDtoResponse(tagRepository
                        .readById(id).orElseThrow(
                                () -> new ValidatorException(String.format(
                                        TAG_ID_DOES_NOT_EXIST.getMessage(),
                                        id
                                ))));
    }

    @Override
    @Transactional(readOnly = true)
    public TagDtoResponse create(TagDtoRequest createRequest) {
        RequestValidator.validateDTORequestForCreate(createRequest);
        return tagMapper
                .tagModelToTagDtoResponse(tagRepository
                        .create(tagMapper
                                .tagModelDtoToTagModel(createRequest)
                        )
                );
    }

    @Override
    @Transactional
    public TagDtoResponse update(TagDtoRequest updateRequest) {
        RequestValidator.validateDTORequestForUpdate(updateRequest);
        if (tagRepository.existById(updateRequest.id())) {
            return tagMapper
                    .tagModelToTagDtoResponse(tagRepository
                            .update(tagMapper
                                    .tagModelDtoToTagModel(updateRequest)));
        } else {
            throw new NotFoundException(String.format(TAG_ID_DOES_NOT_EXIST.getMessage(), updateRequest.id()));
        }
    }

    @Override
    @Transactional
    public Boolean deleteById(Long id) {
        if (tagRepository.existById(id)) {
            return tagRepository.deleteById(id);
        } else {
            throw new NotFoundException(String.format(TAG_ID_DOES_NOT_EXIST.getMessage(), id));
        }
    }

    @Override
    @Transactional
    public List<TagDtoResponse> readByNewsId(Long newsId) {
        return tagMapper.tagModelListToTagDtoResponseList(tagRepository.readByNewsId(newsId));
    }
}
