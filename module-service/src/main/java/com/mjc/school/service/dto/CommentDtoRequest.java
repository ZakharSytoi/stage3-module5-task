package com.mjc.school.service.dto;

/*import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;*/

import com.mjc.school.service.validation.validationGroups.OnCreate;
import com.mjc.school.service.validation.validationGroups.OnUpdate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

public record CommentDtoRequest(
        @Min(value = 1, groups = {OnUpdate.class}, message = "id can't be null or less then 1, id was: '${validatedValue}'")
        @NotNull(groups = {OnUpdate.class}, message = "id can't be null or less then 1, id was: '${validatedValue}'")
        @Null(groups = {OnCreate.class}, message = "id should be null id was: '${validatedValue}'")
        Long id,
        @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "Comment content can't be null")
        @Size(min = 5, max = 255, groups = {OnCreate.class, OnUpdate.class}, message = "Comment content must be between {min} and {max}, Comment content was: '${validatedValue}'")
        String content,
        @Min(value = 1, groups = {OnCreate.class, OnUpdate.class}, message = "News id can't be null or less then 1, id was: '${validatedValue}'")
        @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "News id can't be null or less then 1, id was: '${validatedValue}'")
        Long newsId
) {
}
