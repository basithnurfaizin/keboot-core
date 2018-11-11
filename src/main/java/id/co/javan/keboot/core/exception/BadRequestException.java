package id.co.javan.keboot.core.exception;

public class BadRequestException extends DefaultException {
    private String code;

    public BadRequestException() {
        this(ErrorCode.BAD_REQUEST);
    }

    public BadRequestException(ErrorCode error) {
        super(error.getMessage());
        this.code = error.name();
    }

    public BadRequestException(String code, String msg) {
        super(msg);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
