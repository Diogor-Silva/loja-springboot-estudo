package com.estudo.loja.dto;

import com.estudo.loja.entity.Endereco;

public record EnderecoResponse(
        Long id,
        String rua,
        String numero,
        String bairro,
        String cidade,
        String estado,
        String cep
) {

    public EnderecoResponse(Endereco endereco) {
        this(
                endereco.getId(),
                endereco.getRua(),
                endereco.getNumero(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getCep()
        );
    }
}