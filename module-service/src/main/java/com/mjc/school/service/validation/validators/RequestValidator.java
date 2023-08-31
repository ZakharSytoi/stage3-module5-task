package com.mjc.school.service.validation.validators;

import com.mjc.school.service.exceptions.InvalidRequestException;
import com.mjc.school.service.validation.validationGroups.OnCreate;
import com.mjc.school.service.validation.validationGroups.OnUpdate;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;
@Component
public class RequestValidator {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> void validateDTORequestForUpdate(T request) {
        Set<ConstraintViolation<T>> idViolation = validator.validate(request, OnUpdate.class);
        if (!idViolation.isEmpty()) {
            throw new InvalidRequestException(
                    idViolation
                            .stream()
                            .map(ConstraintViolation::getMessage)
                            .collect(Collectors.toSet()));
        }
    }

    public static <T> void validateDTORequestForCreate(T request) {
        Set<ConstraintViolation<T>> idViolation = validator.validate(request, OnCreate.class);
        if (!idViolation.isEmpty()) {
            throw new InvalidRequestException(
                    idViolation
                            .stream()
                            .map(ConstraintViolation::getMessage)
                            .collect(Collectors.toSet()));
        }
    }
}
