package com.estudo.loja.dto;

import lombok.Data;

@Data
public class ItemCaixaRequest {

    private Long produtoId;
    private Integer quantidade;
}
