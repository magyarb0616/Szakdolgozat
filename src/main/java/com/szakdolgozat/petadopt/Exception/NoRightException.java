package com.szakdolgozat.petadopt.Exception;

public class NoRightException extends RuntimeException{
    private String resourceName;
    private String actionName;

    public NoRightException(String resourceName, String actionName) {
        super(String.format("No right to %s this %s",resourceName, actionName));
        this.resourceName = resourceName;
        this.actionName = actionName;
    }
}
