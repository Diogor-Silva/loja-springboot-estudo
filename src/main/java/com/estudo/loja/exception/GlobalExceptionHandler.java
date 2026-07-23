package com.estudo.loja.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ErroResponse> tratarRegraNegocio(
            RegraNegocioException exception,
            HttpServletRequest request
    ) {
        return criarResposta(
                HttpStatus.CONFLICT,
                exception.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErroResponse> tratarResponseStatus(
            ResponseStatusException exception,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.valueOf(
                exception.getStatusCode().value()
        );

        String mensagem = exception.getReason() != null
                ? exception.getReason()
                : "Não foi possível concluir a operação.";

        return criarResposta(
                status,
                mensagem,
                request.getRequestURI()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> tratarValidacao(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        String mensagem = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
                .distinct()
                .collect(Collectors.joining("; "));

        return criarResposta(
                HttpStatus.BAD_REQUEST,
                mensagem,
                request.getRequestURI()
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErroResponse> tratarIntegridadeBanco(
            DataIntegrityViolationException exception,
            HttpServletRequest request
    ) {
        return criarResposta(
                HttpStatus.CONFLICT,
                "Não foi possível realizar a operação porque o registro possui vínculos com outros dados do sistema.",
                request.getRequestURI()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponse> tratarErroInesperado(
            Exception exception,
            HttpServletRequest request
    ) {
        return criarResposta(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocorreu um erro inesperado. Tente novamente ou entre em contato com o suporte.",
                request.getRequestURI()
        );
    }

    private ResponseEntity<ErroResponse> criarResposta(
            HttpStatus status,
            String mensagem,
            String caminho
    ) {
        ErroResponse resposta = new ErroResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                mensagem,
                caminho
        );

        return ResponseEntity
                .status(status)
                .body(resposta);
    }
}