package com.estudo.loja.dto;

import com.estudo.loja.enums.FormaPagamento;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CaixaRequest {

    private Long clienteId;
    private FormaPagamento formaPagamento;
    private BigDecimal valorPago;
    private List<ItemCaixaRequest> itens;
}