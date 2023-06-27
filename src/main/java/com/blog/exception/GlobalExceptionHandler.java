package com.blog.exception;

import com.blog.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    // handle specific exceptions
    @ExceptionHandler(ResourceNotFoundException.class) // this annotation has a class name which will tell
    // project code that if any ResourceNotFoundException occurs, then call this method.

    public ResponseEntity<ErrorDetails> /*ResponseEntity will bind ErrorDetails with postman response  */
    handleResourceNotFoundException(ResourceNotFoundException exception, /* whenever ResourceNotFoundException occurs
    , it will automatically initilise "exception" variable with that object.
    whenever an exception occurs in contoller layer, contoller layer
    will create an object and that object address will come here -ResourceNotFoundException if it is Resource Not Found Exception
    other exception cannot come here.*/
                                    WebRequest webRequest){ // whenever you are configuring a method for exception handling, there should be an WebRequest present in it.

        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), // initise errorDetails object  and suppy date and message
                webRequest.getDescription(false)); // webrequest will get description of that message.
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND); // return the response back
    }


    // global exceptions - can handle any exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception,
                                                              WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}





// this class will act as catch block and controller layer will act as try block. controller layer will throw exception and spring boot will automatically
//take the exception and give it to this layer.
//rules - whatever class you have created(GlobalExceptionHandler), it has to be sub class of ResponseEntityExceptionHandler
// mark your class as @ControllerAdvice - a class with this annotation, is the class which handles all project exception.

/* @controller - use to build controller layer in web application
@restcontroller - use to build controller layer in web services/developing an API
@ControllerAdvice - use to handle exception in spring boot project
 */


// exception will occurs in either controller or service layer. in spring boot we don't use try catch block. we use annotation
// all exception will go to exception handling class. if we use exception class then that is considered as global exception because
// exception class can handle any exception. any exception other than resource not found exception is global exception