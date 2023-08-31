package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseRestController;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.CommentService;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.TagService;
import com.mjc.school.service.dto.*;
import com.mjc.school.service.query.NewsQueryParams;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/news-management-api/v1/news")
@Api(value = "Allows creating, retrieving, updating and deleting News")
public class NewsController implements BaseRestController<NewsDtoRequest, NewsDtoResponse, Long> {
    private final NewsService newsService;
    private final CommentService commentService;
    private final AuthorService authorService;
    private final TagService tagService;

    @Override
    @GetMapping
    @ApiOperation(value = "retrieve all news")
    @ApiResponses({
            @ApiResponse(code = 200, message = "News successfully retrieved"),
            @ApiResponse(code = 404, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Page<NewsDtoResponse>> readAll(
            @RequestParam(required = false, defaultValue = "0")
            @Parameter(description = "Page number")
            int pageNo,
            @RequestParam(required = false, defaultValue = "10")
            @Parameter(description = "Page size")
            int pageSize) {
        return new ResponseEntity<>(newsService.readAll(PageRequest.of(pageNo, pageSize)), HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/{id:\\d+}")
    @ApiOperation(value = "Retrieve particular news by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "News successfully retrieved"),
            @ApiResponse(code = 404, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<NewsDtoResponse> readById(
            @PathVariable
            @Parameter(description = "id of news to retrieve")
            Long id) {
        return new ResponseEntity<>(newsService.readById(id), HttpStatus.OK);
    }

    @GetMapping("/search")
    @ApiOperation(value = "Retrieve news by params")
    @ApiResponses({
            @ApiResponse(code = 200, message = "News successfully retrieved"),
            @ApiResponse(code = 404, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<List<NewsDtoResponse>> readByQueryParams(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody()
            NewsQueryParams newsQueryParams) {
        return new ResponseEntity<>(newsService.readByQueryParams(newsQueryParams), HttpStatus.OK);
    }

    @GetMapping("/{id:\\d+}/comments")
    @ApiOperation(value = "Retrieve comments by news id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Comments successfully retrieved"),
            @ApiResponse(code = 404, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<List<CommentDtoResponse>> readCommentsByNewsId(
            @PathVariable
            @Parameter(description = "news id")
            Long id) {
        return new ResponseEntity<>(commentService.readByNewsId(id), HttpStatus.OK);
    }

    @GetMapping("/{id:\\d+}/news")
    @ApiOperation(value = "Retrieve author by news id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Author successfully retrieved"),
            @ApiResponse(code = 404, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<AuthorDtoResponse> readAuthorByNewsId(@PathVariable Long id) {
        return new ResponseEntity<>(authorService.readByNewsId(id), HttpStatus.OK);
    }

    @GetMapping("/{id:\\d+}/tags")
    @ApiOperation(value = "retrieve tags by news id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "tags successfully retrieved"),
            @ApiResponse(code = 404, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<List<TagDtoResponse>> readTagsByNewsId(
            @PathVariable
            @Parameter(description = "News id")
            Long id) {
        return new ResponseEntity<>(tagService.readByNewsId(id), HttpStatus.OK);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create news")
    @ApiResponses({
            @ApiResponse(code = 201, message = "News successfully created"),
            @ApiResponse(code = 404, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<NewsDtoResponse> create(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody()
            NewsDtoRequest createRequest) {
        return new ResponseEntity<>(newsService.create(createRequest), HttpStatus.CREATED);
    }

    @Override
    @PutMapping(value = "/{id:\\d+}")
    @ApiOperation(value = "Update news")
    @ApiResponses({
            @ApiResponse(code = 200, message = "News successfully updated"),
            @ApiResponse(code = 404, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<NewsDtoResponse> update(
            @PathVariable
            @Parameter(description = "id of news to update")
            Long id,
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody()
            NewsDtoRequest updateRequest) {
        return new ResponseEntity<>(newsService.update(new NewsDtoRequest(id,
                updateRequest.title(),
                updateRequest.content(),
                updateRequest.authorId(),
                updateRequest.tagIds())), HttpStatus.OK);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete news")
    @ApiResponses({
            @ApiResponse(code = 204, message = "News successfully deleted"),
            @ApiResponse(code = 404, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public void deleteById(
            @PathVariable
            @Parameter(description = "id of news to delete")
            Long id) {
        newsService.deleteById(id);
    }
}
