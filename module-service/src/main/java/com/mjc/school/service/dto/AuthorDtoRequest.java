package com.mjc.school.service.dto;


import com.mjc.school.service.validation.validationGroups.OnCreate;
import com.mjc.school.service.validation.validationGroups.OnUpdate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;


public record AuthorDtoRequest(
        @Min(value = 1, groups = {OnUpdate.class}, message = "id can't be null or less then 1, id was: '${validatedValue}'")
        @NotNull(groups = {OnUpdate.class}, message = "id can't be null or less then 1, id was: '${validatedValue}'")
        @Null(groups = {OnCreate.class}, message = "id should be null id was: '${validatedValue}'")
        Long id,
        @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "Author name can't be null")
        @Size(min = 3, max = 15, groups = {OnCreate.class, OnUpdate.class}, message = "Author name must be between {min} and {max}, Author name was: '${validatedValue}'")
        String name
) {

}
