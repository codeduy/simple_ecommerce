package com.example.demo.exceptions;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppValidationException extends AppException {
    private String field;
    private String errorCode;

    public AppValidationException(String message, String field, String errorCode) {
        super(message);
        this.field = field;
        this.errorCode = errorCode;
    }
}
