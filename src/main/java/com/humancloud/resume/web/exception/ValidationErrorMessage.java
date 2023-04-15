package com.humancloud.resume.web.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class ValidationErrorMessage {
    private HttpStatus status;
    private String message;
    private Map<String, String> error;

//    public ValidationErrorMessage(HttpStatus status, String message, Map<String, String> errors) {
//        this.status = status;
//        this.message=message;
//        Map<String, String> errorMap = new HashMap<>();
//        errorMap.put(message, "");
//        errors.forEach((k,v)->errorMap.put(k,v));
//        this.error = errorMap;
//    }

    public ValidationErrorMessage(HttpStatus status, String message, Map<String, String> errors) {
        this.status = status;
        this.message = message;
        this.error = errors;
    }


}

