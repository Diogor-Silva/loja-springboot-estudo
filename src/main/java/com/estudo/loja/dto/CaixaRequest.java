package com.estudo.loja.dto;

import com.estudo.loja.enums.FormaPagamento;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CaixaRequest {

    @NotNull(message = "Cliente é obrigatório")
    private Long clienteId;

    @NotNull(message = "Forma de pagamento é obrigatória")
    private FormaPagamento formaPagamento;

    private BigDecimal valorPago;

    @Valid
    @NotEmpty(message = "A venda deve possuir pelo menos um produto")
    private List<ItemCaixaRequest> itens;
}