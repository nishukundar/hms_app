package com.hms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

    @ControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(Exception.class)
        public ResponseEntity<String> handleException(Exception ex) {
            // Log the exception (optional)
            ex.printStackTrace();
            return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @ExceptionHandler(IllegalStateException.class)
        public ResponseEntity<String> handleIllegalStateException(IllegalStateException ex) {
            // Log the exception (optional)
            ex.printStackTrace();
            return new ResponseEntity<>("Illegal state: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }




