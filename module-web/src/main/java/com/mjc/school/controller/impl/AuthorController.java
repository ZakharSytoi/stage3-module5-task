package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseRestController;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
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
@RequestMapping("/news-management-api/v1/authors")
@Tag(name = "Authors", description = "Allows creating, retrieving, updating and deleting authors")
public class AuthorController implements BaseRestController<AuthorDtoRequest, AuthorDtoResponse, Long> {
    private final AuthorService authorService;

    @Override
    @GetMapping
    @Operation(summary = "retrieve all authors")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Authors successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
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
    @Operation(summary = "Retrieve particular author by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Author successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
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
    @Operation(summary = "Create author")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Author successfully created"),
            @ApiResponse(responseCode = "404", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<AuthorDtoResponse> create(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody()
            AuthorDtoRequest createRequest) {
        return new ResponseEntity<>(authorService.create(createRequest), HttpStatus.CREATED);
    }

    @Override
    @PutMapping(value = "/{id:\\d+}")
    @Operation(summary = "Update author")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Author successfully updated"),
            @ApiResponse(responseCode = "404", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
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
    @Operation(summary = "Delete author")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Author successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public void deleteById(
            @PathVariable
            @Parameter(description = "id of author to delete")
            Long id) {
        authorService.deleteById(id);
    }
}
