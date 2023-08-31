package com.mjc.school.service.exceptions;

public enum ServiceErrorCode {
    NEWS_ID_DOES_NOT_EXIST(Constants.ERROR_000001, "News with id %d does not exist."),
    AUTHOR_ID_DOES_NOT_EXIST(Constants.ERROR_000001, "Author Id does not exist. Author Id is: %s"),
    TAG_ID_DOES_NOT_EXIST(Constants.ERROR_000001, "Tag Id does not exist. Tag Id is: %s"),
    COMMENT_ID_DOES_NOT_EXIST(Constants.ERROR_000001, "Comment Id does not exist. Tag Id is: %s"),
    VALIDATE_NEGATIVE_OR_NULL_NUMBER(
            Constants.ERROR_000010, "%s can not be null or less than 1. %s is: %s"),
    VALIDATE_NULL_STRING(Constants.ERROR_000011, "%s can not be null. %s is null"),
    VALIDATE_STRING_LENGTH(
            Constants.ERROR_000012, "%s can not be less than %d and more than %d symbols. %s is %s"),
    VALIDATE_INT_VALUE(Constants.ERROR_000013, "%s should be number"),
    NO_VALID_ARGUMENT_GIVEN(Constants.ERROR_000014, "No valid argument is given. At least one argument should not be null"),

    AUTHOR_DOES_NOT_EXIST_FOR_NEWS_ID("000004", "Author not found for news with id %d."),
    VALIDATION("000013", "Validation failed: %s");
    private final String errorMessage;

    ServiceErrorCode(String errorCode, String message) {
        this.errorMessage = "ERROR_CODE: " + errorCode + " ERROR_MESSAGE: " + message;
    }

    public String getMessage() {
        return errorMessage;
    }

    public String getErrorCode() {
        return errorMessage;
    }

    private static class Constants {
        private static final String ERROR_000001 = "000001";
        private static final String ERROR_000002 = "000002";
        private static final String ERROR_000010 = "000010";
        private static final String ERROR_000011 = "000011";
        private static final String ERROR_000012 = "000012";
        private static final String ERROR_000013 = "000013";
        private static final String ERROR_000014 = "000014";

        private Constants() {}
    }
}
