package com.estudo.loja.exception;

import java.time.LocalDateTime;

public record ErroResponse(
        LocalDateTime dataHora,
        int status,
        String erro,
        String mensagem,
        String caminho
) {
}