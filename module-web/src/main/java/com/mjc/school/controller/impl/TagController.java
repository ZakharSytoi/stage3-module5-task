package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseRestController;
import com.mjc.school.service.TagService;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
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
@RequestMapping("/news-management-api/v1/tags")
@Api(value = "Allows creating, retrieving, updating and deleting tags")
public class TagController implements BaseRestController<TagDtoRequest, TagDtoResponse, Long> {

    private final TagService tagService;

    @Override
    @GetMapping
    @ApiOperation(value = "retrieve all tags")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tags successfully retrieved"),
            @ApiResponse(code = 404, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Page<TagDtoResponse>> readAll(
            @RequestParam(required = false, defaultValue = "0")
            @Parameter(description = "Page number")
            int pageNo,
            @RequestParam(required = false, defaultValue = "10")
            @Parameter(description = "Page size")
            int pageSize) {
        return new ResponseEntity<>(tagService.readAll(PageRequest.of(pageNo, pageSize)), HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/{id:\\d+}")
    @ApiOperation(value = "Retrieve particular tag by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tag successfully retrieved"),
            @ApiResponse(code = 404, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<TagDtoResponse> readById(
            @PathVariable
            @Parameter(description = "id of tag to retrieve")
            Long id) {
        return new ResponseEntity<>(tagService.readById(id), HttpStatus.OK);
    }


    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create tag")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Tag successfully created"),
            @ApiResponse(code = 404, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<TagDtoResponse> create(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody()
            TagDtoRequest createRequest) {
        return new ResponseEntity<>(tagService.create(createRequest), HttpStatus.CREATED);
    }

    @Override
    @PutMapping(value = "/{id:\\d+}")
    @ApiOperation(value = "Update tag")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tag successfully updated"),
            @ApiResponse(code = 404, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<TagDtoResponse> update(
            @PathVariable
            @Parameter(description = "id of tag to update")
            Long id,
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody()
            TagDtoRequest updateRequest) {
        return new ResponseEntity<>(tagService.update(new TagDtoRequest(id,
                updateRequest.name())
        ), HttpStatus.OK);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete tag")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Tag successfully deleted"),
            @ApiResponse(code = 404, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public void deleteById(
            @PathVariable
            @Parameter(description = "id of tag to delete")
            Long id) {
        tagService.deleteById(id);
    }
}
