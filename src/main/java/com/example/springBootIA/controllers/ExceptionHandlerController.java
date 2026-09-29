package com.example.springBootIA.controllers;

import com.example.springBootIA.exceptions.BasicException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    //BasicController
    @ExceptionHandler(BasicException.class)
    public String handleBasicException(BasicException ex){
        return ex.getMessage();
    }
}
