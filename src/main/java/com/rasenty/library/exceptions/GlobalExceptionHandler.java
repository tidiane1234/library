package com.rasenty.library.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<?> entityNotFoundException(EntityNotFoundException ex){
        Map<String ,Object> errors = new HashMap<>();
        errors.put("timestamp", LocalDate.now());
        errors.put("status", HttpStatus.NOT_FOUND);
        errors.put("message", ex.getMessage());

        return new ResponseEntity<>(errors,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategorieNotEmptyException.class)
    private ResponseEntity<?> categorieNotEmptyException(CategorieNotEmptyException ex){
        Map<String ,Object> errors = new HashMap<>();
        errors.put("timestamp", LocalDate.now());
        errors.put("status", HttpStatus.BAD_REQUEST);
        errors.put("message", ex.getMessage());

        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidInputException.class)
    private ResponseEntity<?> invalidInputException(InvalidInputException ex){
        Map<String ,Object> errors = new HashMap<>();
        errors.put("timestamp", LocalDate.now());
        errors.put("status", HttpStatus.BAD_REQUEST);
        errors.put("message", ex.getMessage());

        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handlerNotValidException(MethodArgumentNotValidException ex){
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->{
            errors.put(error.getField(),error.getDefaultMessage());
        });

        Map<String ,Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("message", "Erreur lors de la validation des données ");
        response.put("error", errors);

        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);


    }
}
