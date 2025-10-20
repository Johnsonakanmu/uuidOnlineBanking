package com.johnsonlovecode.USSDCreationApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    private String resourceName;
    private  String fileName;
    private Long fileValue;

    public ResourceNotFoundException(String resourceName, String fileName, Long fileValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fileName, fileValue));
        this.resourceName =resourceName;
        this.fileName = fileName;
        this.fileValue =fileValue;
    }
}
