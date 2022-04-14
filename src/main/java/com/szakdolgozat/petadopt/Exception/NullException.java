package com.szakdolgozat.petadopt.Exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class NullException extends RuntimeException{
    private String resourceName;
    private String fieldName;

    public NullException(String resourceName, String fieldName) {
        super(String.format("%s.%s cannot be null!",resourceName, fieldName));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
