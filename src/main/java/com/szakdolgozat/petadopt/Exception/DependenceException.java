package com.szakdolgozat.petadopt.Exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DependenceException extends RuntimeException{

    public DependenceException() {
        super("Dependence Exception");
    }

    public DependenceException(String message) {
        super("Dependence Exception: "+message);
    }
}
