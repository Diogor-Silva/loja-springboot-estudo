package com.estudo.loja.service;



import com.estudo.loja.dto.ProdutoDTO;
import com.estudo.loja.entity.Produto;
import com.estudo.loja.repository.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public List<ProdutoDTO> listar() {
        return repository.findAll()
                .stream()
                .map(ProdutoDTO::new)
                .toList();
    }

    public Produto salvar(Produto produto) {
        return repository.save(produto);
    }

    public Produto atualizar(Long id, Produto produto) {
        Produto produtoBanco = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Produto com id " + id + " não encontrado"
                ));

        produtoBanco.setNome(produto.getNome());
        produtoBanco.setDescricao(produto.getDescricao());
        produtoBanco.setMarca(produto.getMarca());
        produtoBanco.setCategoria(produto.getCategoria());
        produtoBanco.setPreco(produto.getPreco());
        produtoBanco.setQuantidadeEstoque(produto.getQuantidadeEstoque());
        produtoBanco.setCodigoBarra(produto.getCodigoBarra());

        return repository.save(produtoBanco);
    }

    public void excluir(Long id) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Produto com id " + id + " não encontrado"
                ));

        repository.delete(produto);
    }
}
