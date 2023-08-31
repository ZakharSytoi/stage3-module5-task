package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseRestController;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/news-management-api/v1/authors")
@Api(value = "Allows creating, retrieving, updating and deleting authors")
public class AuthorController implements BaseRestController<AuthorDtoRequest, AuthorDtoResponse, Long> {
    private final AuthorService authorService;

    @Override
    @GetMapping
    @ApiOperation(value = "retrieve all authors")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Authors successfully retrieved"),
            @ApiResponse(code = 404, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Page<AuthorDtoResponse>> readAll(
            @RequestParam(required = false, defaultValue = "0")
            @Parameter(description = "Page number")
            int pageNo,
            @RequestParam(required = false, defaultValue = "10")
            @Parameter(description = "Page size")
            int pageSize) {
        return new ResponseEntity<>(authorService.readAll(PageRequest.of(pageNo, pageSize)), HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/{id:\\d+}")
    @ApiOperation(value = "Retrieve particular author by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Author successfully retrieved"),
            @ApiResponse(code = 404, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<AuthorDtoResponse> readById(
            @PathVariable
            @Parameter(description = "id of author to retrieve")
            Long id) {
        return new ResponseEntity<>(authorService.readById(id), HttpStatus.OK);
    }


    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create author")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Author successfully created"),
            @ApiResponse(code = 404, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<AuthorDtoResponse> create(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody()
            AuthorDtoRequest createRequest) {
        return new ResponseEntity<>(authorService.create(createRequest), HttpStatus.CREATED);
    }

    @Override
    @PutMapping(value = "/{id:\\d+}")
    @ApiOperation(value = "Update author")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Author successfully updated"),
            @ApiResponse(code = 404, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<AuthorDtoResponse> update(
            @Parameter(description = "id of author to update")
            @PathVariable
            Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody()
            @RequestBody
            AuthorDtoRequest updateRequest) {
        return new ResponseEntity<>(authorService.update(new AuthorDtoRequest(id,
                updateRequest.name())
        ), HttpStatus.OK);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete author")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Author successfully deleted"),
            @ApiResponse(code = 404, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public void deleteById(
            @PathVariable
            @Parameter(description = "id of author to delete")
            Long id) {
        authorService.deleteById(id);
    }
}
