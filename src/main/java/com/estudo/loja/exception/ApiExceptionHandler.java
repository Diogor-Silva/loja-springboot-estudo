 package com.estudo.loja.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

 @RestControllerAdvice
 public class ApiExceptionHandler {

     @ExceptionHandler(ResponseStatusException.class)
     public ResponseEntity<ErrorResponseDTO> tratarResponseStatusException(ResponseStatusException ex) {
         ErrorResponseDTO erro = new ErrorResponseDTO(
                 LocalDateTime.now(),
                 ex.getStatusCode().value(),
                 HttpStatus.valueOf(ex.getStatusCode().value()).getReasonPhrase(),
                 ex.getReason()
         );

         return ResponseEntity.status(ex.getStatusCode()).body(erro);
     }

     @ExceptionHandler(MethodArgumentNotValidException.class)
     public ResponseEntity<Map<String, String>> tratarValidacao(MethodArgumentNotValidException ex) {

         Map<String, String> erros = new HashMap<>();

         for (FieldError erro : ex.getBindingResult().getFieldErrors()) {
             erros.put(erro.getField(), erro.getDefaultMessage());
         }

         return ResponseEntity.badRequest().body(erros);
     }
 }