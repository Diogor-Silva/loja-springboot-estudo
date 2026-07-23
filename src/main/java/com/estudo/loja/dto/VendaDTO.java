package com.estudo.loja.dto;

import com.estudo.loja.entity.Caixa;
import com.estudo.loja.enums.FormaPagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record VendaDTO(
        Long id,
        LocalDateTime dataVenda,
        Long clienteId,
        String clienteNome,
        String clienteCpf,
        FormaPagamento formaPagamento,
        BigDecimal valorTotal,
        BigDecimal valorPago,
        BigDecimal troco,
        Integer quantidadeItens,
        List<ItemVendaDTO> itens
) {

    public VendaDTO(Caixa caixa) {
        this(
                caixa.getId(),
                caixa.getDataVenda(),
                caixa.getCliente().getId(),
                caixa.getCliente().getNome(),
                caixa.getCliente().getCpf(),
                caixa.getFormaPagamento(),
                caixa.getValorTotal(),
                caixa.getValorPago(),
                caixa.getTroco(),
                calcularQuantidadeItens(caixa),
                caixa.getItens()
                        .stream()
                        .map(ItemVendaDTO::new)
                        .toList()
        );
    }

    private static Integer calcularQuantidadeItens(Caixa caixa) {
        return caixa.getItens()
                .stream()
                .mapToInt(item ->
                        item.getQuantidade() == null
                                ? 0
                                : item.getQuantidade()
                )
                .sum();
    }
}