package com.estudo.loja.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
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
        if (repository.existsByCpf(cliente.getCpf())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF já cadastrado");
        }

        if (repository.existsByEmail(cliente.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "E-mail já cadastrado");
        }

        return repository.save(cliente);
    }

    public Cliente atualizar(Long id, Cliente cliente) {
        Cliente clienteBanco = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cliente com id " + id + " não encontrado"
                ));

        if (!clienteBanco.getCpf().equals(cliente.getCpf()) && repository.existsByCpf(cliente.getCpf())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF já cadastrado");
        }

        if (!clienteBanco.getEmail().equals(cliente.getEmail()) && repository.existsByEmail(cliente.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "E-mail já cadastrado");
        }

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
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cliente com id " + id + " não encontrado"
                ));

        repository.delete(cliente);
    }
}