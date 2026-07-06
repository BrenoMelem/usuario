package com.javanauta.usuario.controller;

import com.javanauta.usuario.infraestructure.exceptions.ConflictException;
import com.javanauta.usuario.infraestructure.exceptions.IllegalArgumentExceptions;
import com.javanauta.usuario.infraestructure.exceptions.ResourceNotFoundException;
import com.javanauta.usuario.infraestructure.exceptions.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler  {
    //Implementar tratamento global de exceções para a aplicação, garantindo respostas consistentes e informativas em caso de erros.
    //Como Estamos tratando uma controller, precisamos do ResponseEntity, para retorno do Status HTTP;
    @ExceptionHandler (ResourceNotFoundException.class)
    public ResponseEntity<String> handlerResourceNotFoundException(ResourceNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler (ConflictException.class)
    public ResponseEntity<String> handlerConflictException(ConflictException exception){
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.CONFLICT);
    }
    @ExceptionHandler (UnauthorizedException.class)
    public ResponseEntity<String> handlerUnauthorizedException(UnauthorizedException exception){
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler (IllegalArgumentExceptions.class)
    public ResponseEntity<String> handlerIllegalArgumentException (IllegalArgumentExceptions exceptions){
        return new ResponseEntity<>(exceptions.getMessage(),HttpStatus.BAD_REQUEST);
    }
}
