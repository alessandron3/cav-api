package com.volanty.controller;


import com.volanty.exception.DateNotFoundException;
import com.volanty.exception.TimeAlreadyScheduledException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice(basePackages = "com.volanty.controller")
public class CavControllerExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(TimeAlreadyScheduledException.class)
    public ResponseEntity scheduleVisitTimeNotEmpty() {
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }

    @ExceptionHandler(DateNotFoundException.class)
    public void dateNotFound(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
}
