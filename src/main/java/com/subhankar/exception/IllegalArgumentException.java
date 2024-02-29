package com.subhankar.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IllegalArgumentException extends RuntimeException{
    private String fieldName;
    private String message;
    public IllegalArgumentException(String fieldName, String message) {
        super(String.format("Please Fix %s : '%s'", fieldName, message));
        this.fieldName = fieldName;
        this.message = message;
    }

}
