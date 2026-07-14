package com.estudo.loja.service;

import com.estudo.loja.dto.UsuarioDTO;
import com.estudo.loja.entity.Usuario;
import com.estudo.loja.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
            UsuarioRepository repository,
            PasswordEncoder passwordEncoder
    ) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UsuarioDTO> listar() {
        return repository.findAll()
                .stream()
                .map(UsuarioDTO::new)
                .toList();
    }

    public Usuario salvar(Usuario usuario) {
        if (repository.existsByEmail(usuario.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "E-mail já cadastrado"
            );
        }

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        return repository.save(usuario);
    }

    public Usuario atualizar(Long id, Usuario usuario) {
        Usuario usuarioBanco = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuário com id " + id + " não encontrado"
                ));

        if (!usuarioBanco.getEmail().equals(usuario.getEmail())
                && repository.existsByEmail(usuario.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "E-mail já cadastrado"
            );
        }

        usuarioBanco.setNome(usuario.getNome());
        usuarioBanco.setEmail(usuario.getEmail());
        usuarioBanco.setPerfil(usuario.getPerfil());

        if (usuario.getSenha() != null && !usuario.getSenha().isBlank()) {
            usuarioBanco.setSenha(
                    passwordEncoder.encode(usuario.getSenha())
            );
        }

        return repository.save(usuarioBanco);
    }

    public void excluir(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuário com id " + id + " não encontrado"
                ));

        repository.delete(usuario);
    }
}