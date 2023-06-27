package com.blog.payload;

import java.util.Date;

public class ErrorDetails {
    private Date timestamp; // need did this exception occurs
    private String message;
    private String details;

    // create a constructor which will act as setter
    //whenever an exception will occurs, we will create object of ErrorDetails, to this object we will supply these 3 things, so object will get intialized.
    // constructor when used for setting up value in object, is more easy because in one part all variables are intialised. so it is best way to build setters.
    // in place of it we can also use setter methods.
    public ErrorDetails(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    //getters
    public Date getTimestamp() {
        return timestamp;
    }
    public String getMessage() {
        return message;
    }
    public String getDetails() {
        return details;
    }
}



//payload is the one which go to response entity. so this will go to response of postman.
// three thing you need to give to postman.
