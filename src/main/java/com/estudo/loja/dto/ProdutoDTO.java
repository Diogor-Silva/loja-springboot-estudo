package com.estudo.loja.dto;

import com.estudo.loja.entity.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {

    private Long id;
    private String nome;
    private String descricao;
    private String marca;
    private String categoria;
    private BigDecimal preco;
    private Integer quantidadeEstoque;
    private String codigoBarra;

    public ProdutoDTO(Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.descricao = produto.getDescricao();
        this.marca = produto.getMarca();
        this.categoria = produto.getCategoria();
        this.preco = produto.getPreco();
        this.quantidadeEstoque = produto.getQuantidadeEstoque();
        this.codigoBarra = produto.getCodigoBarra();
    }
}
