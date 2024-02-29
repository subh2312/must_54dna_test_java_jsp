package com.subhankar.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnauthorizedException extends RuntimeException{

    public UnauthorizedException() {
        super(String.format("Invalid username or password.Please try again!!"));

    }
}
