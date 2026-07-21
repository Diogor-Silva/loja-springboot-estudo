package com.estudo.loja.service;

import com.estudo.loja.dto.ClienteDTO;
import com.estudo.loja.dto.ClienteRequest;
import com.estudo.loja.dto.EnderecoRequest;
import com.estudo.loja.entity.Cliente;
import com.estudo.loja.entity.Endereco;
import com.estudo.loja.repository.ClienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Locale;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<ClienteDTO> listar() {
        return repository.findAll()
                .stream()
                .map(ClienteDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public ClienteDTO buscarPorId(Long id) {
        return new ClienteDTO(buscarEntidade(id));
    }

    @Transactional
    public ClienteDTO salvar(ClienteRequest request) {
        String cpf = somenteNumeros(request.cpf());
        String telefone = somenteNumeros(request.telefone());
        String email = normalizarEmail(request.email());

        validarCadastroDuplicado(cpf, email);

        Endereco endereco = criarEndereco(request.endereco());

        Cliente cliente = new Cliente();
        cliente.setNome(request.nome().trim());
        cliente.setEmail(email);
        cliente.setTelefone(telefone);
        cliente.setCpf(cpf);
        cliente.setGenero(request.genero());
        cliente.setEndereco(endereco);

        return new ClienteDTO(repository.save(cliente));
    }

    @Transactional
    public ClienteDTO atualizar(Long id, ClienteRequest request) {
        Cliente cliente = buscarEntidade(id);

        String cpf = somenteNumeros(request.cpf());
        String telefone = somenteNumeros(request.telefone());
        String email = normalizarEmail(request.email());

        if (repository.existsByCpfAndIdNot(cpf, id)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "CPF já cadastrado"
            );
        }

        if (repository.existsByEmailIgnoreCaseAndIdNot(email, id)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "E-mail já cadastrado"
            );
        }

        cliente.setNome(request.nome().trim());
        cliente.setEmail(email);
        cliente.setTelefone(telefone);
        cliente.setCpf(cpf);
        cliente.setGenero(request.genero());

        atualizarEndereco(
                cliente.getEndereco(),
                request.endereco()
        );

        return new ClienteDTO(repository.save(cliente));
    }

    @Transactional
    public void excluir(Long id) {
        Cliente cliente = buscarEntidade(id);
        repository.delete(cliente);
    }

    private Cliente buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cliente com id " + id + " não encontrado"
                ));
    }

    private void validarCadastroDuplicado(
            String cpf,
            String email
    ) {
        if (repository.existsByCpf(cpf)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "CPF já cadastrado"
            );
        }

        if (repository.existsByEmailIgnoreCase(email)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "E-mail já cadastrado"
            );
        }
    }

    private Endereco criarEndereco(EnderecoRequest request) {
        Endereco endereco = new Endereco();
        atualizarEndereco(endereco, request);
        return endereco;
    }

    private void atualizarEndereco(
            Endereco endereco,
            EnderecoRequest request
    ) {
        endereco.setRua(request.rua().trim());
        endereco.setNumero(request.numero().trim());
        endereco.setBairro(request.bairro().trim());
        endereco.setCidade(request.cidade().trim());
        endereco.setEstado(
                request.estado().trim().toUpperCase(Locale.ROOT)
        );
        endereco.setCep(somenteNumeros(request.cep()));
    }

    private String somenteNumeros(String valor) {
        return valor == null
                ? ""
                : valor.replaceAll("\\D", "");
    }

    private String normalizarEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }
}