package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseRestController;
import com.mjc.school.service.CommentService;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/news-management-api/v1/comments")
@Api(value = "Allows creating, retrieving, updating and deleting comments")
public class CommentController implements BaseRestController<CommentDtoRequest, CommentDtoResponse, Long> {

    private final CommentService commentService;

    @Override
    @GetMapping
    @ApiOperation(value = "retrieve all comments")
    @ApiResponses({
            @ApiResponse(code = 200, message = "comments successfully retrieved"),
            @ApiResponse(code = 404, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Page<CommentDtoResponse>> readAll(
            @RequestParam(required = false, defaultValue = "0")
            @Parameter(description = "Page number")
            int pageNo,
            @RequestParam(required = false, defaultValue = "10")
            @Parameter(description = "Page size")
            int pageSize) {
        return new ResponseEntity<>(commentService.readAll(PageRequest.of(pageNo, pageSize)), HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/{id:\\d+}")
    @ApiOperation(value = "Retrieve particular comment by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Comment successfully retrieved"),
            @ApiResponse(code = 404, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<CommentDtoResponse> readById(
            @PathVariable
            @Parameter(description = "id of comment to retrieve")
            Long id) {
        return new ResponseEntity<>(commentService.readById(id), HttpStatus.OK);
    }


    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create comment")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Comment successfully created"),
            @ApiResponse(code = 404, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<CommentDtoResponse> create(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody()
            CommentDtoRequest createRequest) {
        return new ResponseEntity<>(commentService.create(createRequest), HttpStatus.CREATED);
    }

    @Override
    @PutMapping(value = "/{id:\\d+}")
    @ApiOperation(value = "Update comment")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Comment successfully updated"),
            @ApiResponse(code = 404, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<CommentDtoResponse> update(
            @PathVariable
            @Parameter(description = "id of comment to update")
            Long id,
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody()
            CommentDtoRequest updateRequest) {
        return new ResponseEntity<>(commentService.update(new CommentDtoRequest(id, updateRequest.content(), updateRequest.newsId())), HttpStatus.OK);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete comment")
    @ApiResponses({
            @ApiResponse(code = 204, message = "comment successfully deleted"),
            @ApiResponse(code = 404, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public void deleteById(
            @PathVariable
            @Parameter(description = "id of comment to delete")
            Long id) {
        commentService.deleteById(id);
    }
}
