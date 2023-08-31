package com.mjc.school.service.exceptions;

import java.util.Set;

public class InvalidRequestException extends RuntimeException{
    private Set<String> errorMessages;

    public InvalidRequestException(Set<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    @Override
    public String getMessage() {
        return String.join("\n", errorMessages);
    }
}
