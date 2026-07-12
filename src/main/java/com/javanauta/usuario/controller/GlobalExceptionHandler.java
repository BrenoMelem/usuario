package com.javanauta.usuario.controller;

import com.javanauta.usuario.infraestructure.exceptions.ConflictException;
import com.javanauta.usuario.infraestructure.exceptions.IllegalArgumentExceptions;
import com.javanauta.usuario.infraestructure.exceptions.ResourceNotFoundException;
import com.javanauta.usuario.infraestructure.exceptions.UnauthorizedException;
import com.javanauta.usuario.infraestructure.exceptions.dto.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    //Implementar tratamento global de exceções para a aplicação, garantindo respostas consistentes e informativas em caso de erros.
    //Como Estamos tratando uma controller, precisamos do ResponseEntity, para retorno do Status HTTP;
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handlerResourceNotFoundException(ResourceNotFoundException exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildError(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                request.getRequestURI(),
                "NOT FOUND"
        ));

    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponseDto> handlerConflictException(ConflictException exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(buildError(
                HttpStatus.CONFLICT.value(),
                exception.getMessage(),
                request.getRequestURI(),
                "CONFLICT"
        ));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponseDto> handlerUnauthorizedException(UnauthorizedException exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(buildError(
                HttpStatus.UNAUTHORIZED.value(),
                exception.getMessage(),
                request.getRequestURI(),
                "UNAUTHORIZED"
        ));
    }

    @ExceptionHandler(IllegalArgumentExceptions.class)
    public ResponseEntity<ErrorResponseDto> handlerIllegalArgumentException(IllegalArgumentExceptions exceptions, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildError(
                HttpStatus.BAD_REQUEST.value(),
                exceptions.getMessage(),
                request.getRequestURI(),
                "BAD REQUEST"
        ));
    }

    private ErrorResponseDto buildError(int status, String mensagem, String path, String error) {
        return ErrorResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .status(status)
                .error(error)
                .message(mensagem)
                .path(path)
                .build();
    }
}
