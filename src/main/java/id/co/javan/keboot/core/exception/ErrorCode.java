package id.co.javan.keboot.core.exception;

public enum ErrorCode {
    INTERNAL_SERVER_ERROR("Internal server error"),
    BAD_REQUEST("Bad Request"),
    UNAUTHORIZED("Unauthorized"),
    FORBIDDEN("Forbidden"),
    ERROR_VALIDATION("Error Validation"),
    DATA_NOT_FOUND("Data not found");

    private String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
