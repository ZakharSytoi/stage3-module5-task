package com.mjc.school.service.impl;

import com.mjc.school.repository.CommentRepository;
import com.mjc.school.service.CommentService;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ValidatorException;
import com.mjc.school.service.mappers.CommentMapper;
import com.mjc.school.service.paging.PagingUtil;
import com.mjc.school.service.validation.validators.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mjc.school.service.exceptions.ServiceErrorCode.COMMENT_ID_DOES_NOT_EXIST;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository,
                              CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CommentDtoResponse> readAll(Pageable pageRequest) {
        List<CommentDtoResponse> commentList = commentMapper.commentModelListToCommentDtoList(commentRepository.readAll());
        return PagingUtil.createPageFromList(pageRequest, commentList);
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDtoResponse readById(Long id) {
        return commentMapper
                .commentModelToCommentDtoResponse(commentRepository
                        .readById(id).orElseThrow(
                                () -> new ValidatorException(String.format(
                                        COMMENT_ID_DOES_NOT_EXIST.getMessage(),
                                        id
                                ))));
    }

    @Override
    @Transactional
    public CommentDtoResponse create(CommentDtoRequest createRequest) {
        RequestValidator.validateDTORequestForCreate(createRequest);
        return commentMapper
                .commentModelToCommentDtoResponse(commentRepository
                        .create(commentMapper
                                .commentDotRequestToCommentModel(createRequest)
                        )
                );
    }

    @Override
    @Transactional
    public CommentDtoResponse update(CommentDtoRequest updateRequest) {
        RequestValidator.validateDTORequestForUpdate(updateRequest);
        if (commentRepository.existById(updateRequest.id())) {
            return commentMapper
                    .commentModelToCommentDtoResponse(commentRepository
                            .update(commentMapper
                                    .commentDotRequestToCommentModel(updateRequest)));
        } else {
            throw new NotFoundException(String.format(COMMENT_ID_DOES_NOT_EXIST.getMessage(), updateRequest.id()));
        }
    }

    @Override
    @Transactional
    public Boolean deleteById(Long id) {
        if (commentRepository.existById(id)) {
            return commentRepository.deleteById(id);
        } else {
            throw new NotFoundException(String.format(COMMENT_ID_DOES_NOT_EXIST.getMessage(), id));
        }
    }


    @Override
    public List<CommentDtoResponse> readByNewsId(Long id) {
        return commentMapper.commentModelListToCommentDtoList(commentRepository.readCommentsByNewsId(id));
    }
}
