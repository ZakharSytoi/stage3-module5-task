package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseRestController;
import com.mjc.school.service.TagService;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@Tag(name = "Tags", description = "Allows creating, retrieving, updating and deleting tags")
public class TagController implements BaseRestController<TagDtoRequest, TagDtoResponse, Long> {

    private final TagService tagService;

    @Override
    @GetMapping
    @Operation(summary = "retrieve all tags")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tags successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
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
    @Operation(summary = "Retrieve particular tag by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tag successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
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
    @Operation(summary = "Create tag")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Tag successfully created"),
            @ApiResponse(responseCode = "404", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<TagDtoResponse> create(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody()
            TagDtoRequest createRequest) {
        return new ResponseEntity<>(tagService.create(createRequest), HttpStatus.CREATED);
    }

    @Override
    @PutMapping(value = "/{id:\\d+}")
    @Operation(summary = "Update tag")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tag successfully updated"),
            @ApiResponse(responseCode = "404", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
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
    @Operation(summary = "Delete tag")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Tag successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public void deleteById(
            @PathVariable
            @Parameter(description = "id of tag to delete")
            Long id) {
        tagService.deleteById(id);
    }
}
