package com.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
//whenever the resourse is not found, create object of this class
@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{ // HELP IN BUILDING CUSTOM EXCEPTION CLASS.
    public ResourceNotFoundException(String msg){ // CONSTRUCTOR
        super(msg);
    }
}
