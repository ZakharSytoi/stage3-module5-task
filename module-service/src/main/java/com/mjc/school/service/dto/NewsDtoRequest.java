package com.mjc.school.service.dto;

/*import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;*/

import com.mjc.school.service.validation.validationGroups.OnCreate;
import com.mjc.school.service.validation.validationGroups.OnUpdate;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

public record NewsDtoRequest(
        @Min(value = 1, groups = {OnUpdate.class}, message = "id can't be null or less then 1, id was: '${validatedValue}'")
        @NotNull(groups = {OnUpdate.class}, message = "id can't be null or less then 1, id was: '${validatedValue}'")
        @Null(groups = {OnCreate.class}, message = "id should be null id was: '${validatedValue}'")
        Long id,
        @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "News title can't be null")
        @Size(min = 3, max = 30, groups = {OnCreate.class, OnUpdate.class}, message = "News title must be between {min} and {max}, title name was: '${validatedValue}'")
        String title,
        @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "News content can't be null")
        @Size(min = 3, max = 255, groups = {OnCreate.class, OnUpdate.class}, message = "News content must be between {min} and {max}, content name was: '${validatedValue}'")
        String content,
        @Min(value = 1, groups = {OnCreate.class,OnUpdate.class}, message = "Author id can't be null or less then 1, id was: '${validatedValue}'")
        @NotNull(groups = {OnCreate.class,OnUpdate.class}, message = "Author id can't be null or less then 1, id was: '${validatedValue}'")
        Long authorId,

        @NotNull(groups = {OnCreate.class,OnUpdate.class}, message = "Tag id's can't be null id's was: '${validatedValue}'")
        @Size(min = 1, groups = {OnCreate.class,OnUpdate.class}, message = "Tag id's can't be empty id's was: '${validatedValue}'")
        List<Long> tagIds
) {
}
