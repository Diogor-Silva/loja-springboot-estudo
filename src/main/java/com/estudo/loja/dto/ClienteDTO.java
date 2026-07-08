package com.estudo.loja.dto;

import com.estudo.loja.entity.Cliente;
import com.estudo.loja.entity.Endereco;
import com.estudo.loja.enums.Genero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private Genero genero;
    private Endereco endereco;

    public ClienteDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.email = cliente.getEmail();
        this.telefone = cliente.getTelefone();
        this.cpf = cliente.getCpf();
        this.genero = cliente.getGenero();
        this.endereco = cliente.getEndereco();
    }
}