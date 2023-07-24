package com.anil.airreportbe.Exception;

import com.anil.airreportbe.response.AppResponse;
import com.anil.airreportbe.util.AppUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionControllerHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<AppResponse> userNotFound(NotFoundException exception) {
        return AppUtil.errorResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<AppResponse> customException(CustomException exception) {
        return AppUtil.errorResponse(HttpStatus.FORBIDDEN, exception.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<AppResponse> badRequestException(BadRequestException exception) {
        return AppUtil.errorResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }
}
