package com.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//whenever the resourse is not found, create object of this class
@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class BlogAPIException extends RuntimeException{ // HELP IN BUILDING CUSTOM EXCEPTION CLASS.
    public BlogAPIException(HttpStatus badRequest, String msg){ // CONSTRUCTOR
        super(msg);
    }
}
