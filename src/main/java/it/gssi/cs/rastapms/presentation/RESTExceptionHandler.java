package it.gssi.cs.rastapms.presentation;

import it.gssi.cs.rastapms.presentation.api.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice("it.gssi.cs.rastapms.presentation.api")
public class RESTExceptionHandler {

    @ExceptionHandler(ItineraryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String itineraryNotFoundExceptionHandler(ItineraryNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(ItineraryException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String itineraryExceptionHandler(ItineraryException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(POIException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String poiExceptionHandler(POIException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(POINotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String poiNotFoundExceptionHandler(POINotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HandlerMethodValidationException.class)
    public String handleValidationExceptions(HandlerMethodValidationException ex) {
        Map<String, String> errors = new HashMap<>();
        return (String)ex.getDetailMessageArguments()[0];
    }
}
