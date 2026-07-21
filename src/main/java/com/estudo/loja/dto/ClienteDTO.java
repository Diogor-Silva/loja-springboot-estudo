package com.estudo.loja.dto;

import com.estudo.loja.entity.Cliente;
import com.estudo.loja.enums.Genero;

public record ClienteDTO(
        Long id,
        String nome,
        String email,
        String telefone,
        String cpf,
        Genero genero,
        EnderecoResponse endereco
) {

    public ClienteDTO(Cliente cliente) {
        this(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getTelefone(),
                cliente.getCpf(),
                cliente.getGenero(),
                new EnderecoResponse(cliente.getEndereco())
        );
    }
}