package com.estudo.loja.dto;

import com.estudo.loja.entity.ItemCaixa;

import java.math.BigDecimal;

public record ItemVendaDTO(
        Long id,
        Long produtoId,
        String produtoNome,
        String codigoBarra,
        Integer quantidade,
        BigDecimal valorUnitario,
        BigDecimal subtotal
) {

    public ItemVendaDTO(ItemCaixa item) {
        this(
                item.getId(),
                item.getProduto().getId(),
                item.getProduto().getNome(),
                item.getProduto().getCodigoBarra(),
                item.getQuantidade(),
                item.getValorUnitario(),
                item.getSubtotal()
        );
    }
}