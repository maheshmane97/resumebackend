package com.humancloud.resume.web.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.humancloud.resume.web.utils.BaseResponseDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseResponseDTO> handleBaseException(BaseException exception) {
        BaseResponseDTO responseDTO = BaseResponseDTO.builder()
                .code(exception.getCode())
                .message(exception.getMessage())
                .build();
        return ResponseEntity.ok(responseDTO);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        BaseResponseDTO dto = new BaseResponseDTO(String.valueOf(HttpStatusCode.valueOf(400)), ex.getLocalizedMessage());
        return new ResponseEntity<>(dto, status);
    }

//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    public ResponseEntity<ValidationErrorMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
//        Throwable cause = ex.getCause();
//        ValidationErrorMessage errorMessage = new ValidationErrorMessage();
//        errorMessage.setStatus(HttpStatus.BAD_REQUEST);
//        errorMessage.setMessage("Invalid input");
//
//        if (cause instanceof InvalidFormatException) {
//            InvalidFormatException ife = (InvalidFormatException) cause;
//            if (ife.getTargetType().isEnum()) {
//                errorMessage.setError(Collections.singletonMap(ife.getPath().get(0).getFieldName(), "Invalid value for " + ife.getTargetType().getSimpleName()));
//                return ResponseEntity.badRequest().body(errorMessage);
//            }
//        }
//
//        return ResponseEntity.badRequest().body(errorMessage);
//    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e -> errors.put(e.getField(), e.getDefaultMessage()));

        ValidationErrorMessage errorMessage = new ValidationErrorMessage(HttpStatus.BAD_REQUEST, "Validation Failed", errors);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

}
