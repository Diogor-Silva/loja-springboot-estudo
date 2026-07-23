package com.estudo.loja.dto;

import java.math.BigDecimal;

public record ResumoDashboardDTO(
        long totalClientes,
        long totalProdutos,
        long vendasHoje,
        BigDecimal totalDia
) {
}