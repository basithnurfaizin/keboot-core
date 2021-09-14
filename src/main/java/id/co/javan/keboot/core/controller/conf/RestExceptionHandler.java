package id.co.javan.keboot.core.controller.conf;

import id.co.javan.keboot.core.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { Exception.class})
    protected ResponseEntity<Object> handleCustomException(Exception ex, WebRequest request) {
        // do something with error message, save to log maybe? TODO

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // send response
        Map<String, Object> response = new HashMap<>();

        if(ex instanceof BadRequestException) {
            response.put("error_code", ((BadRequestException) ex).getCode());
            response.put("error_message", ex.getMessage());

            return handleExceptionInternal(ex, response,
                    headers, HttpStatus.BAD_REQUEST, request);
        }

        if(ex instanceof ValidationException) {
            response.put("error_code", ex.getMessage());
            response.put("data", ((ValidationException) ex).getErrors());

            return handleExceptionInternal(ex, response,
                    headers, HttpStatus.BAD_REQUEST, request);
        }

        if(ex instanceof UnauthorizedException) {
            response.put("error_code", ErrorCode.UNAUTHORIZED.name());
            response.put("error_message", ex.getMessage());
            return handleExceptionInternal(ex, response,
                    headers, HttpStatus.UNAUTHORIZED, request);
        }

        if(ex instanceof ForbiddenException) {
            response.put("error_code", ErrorCode.FORBIDDEN.name());
            response.put("error_message", ex.getMessage());
            return handleExceptionInternal(ex, response,
                    headers, HttpStatus.FORBIDDEN, request);
        }

        if(ex instanceof NoSuchElementException) {
            response.put("error_code", ErrorCode.DATA_NOT_FOUND.name());
            response.put("error_message", ErrorCode.DATA_NOT_FOUND.name());
            return handleExceptionInternal(ex, response,
                    headers, HttpStatus.NOT_FOUND, request);
        }

        // here is unexpected exception part
        try {
//            Sentry.capture(ex);
            ex.printStackTrace();
        } catch (Exception e) {
            // nothing do to here
        }


        response.put("error_message", ex.getMessage());
        response.put("error_code", ErrorCode.INTERNAL_SERVER_ERROR.name());

        return handleExceptionInternal(ex, response,
                headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
