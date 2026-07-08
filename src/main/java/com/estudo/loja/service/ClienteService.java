package com.estudo.loja.service;

import com.estudo.loja.dto.ClienteDTO;
import com.estudo.loja.entity.Cliente;
import com.estudo.loja.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public List<ClienteDTO> listar() {
        return repository.findAll()
                .stream()
                .map(ClienteDTO::new)
                .toList();
    }

    public Cliente salvar(Cliente cliente) {
        return repository.save(cliente);
    }

    public Cliente atualizar(Long id, Cliente cliente) {
        Cliente clienteBanco = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        clienteBanco.setNome(cliente.getNome());
        clienteBanco.setEmail(cliente.getEmail());
        clienteBanco.setTelefone(cliente.getTelefone());
        clienteBanco.setCpf(cliente.getCpf());
        clienteBanco.setGenero(cliente.getGenero());
        clienteBanco.setEndereco(cliente.getEndereco());

        return repository.save(clienteBanco);
    }

    public void excluir(Long id) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        repository.delete(cliente);
    }
}