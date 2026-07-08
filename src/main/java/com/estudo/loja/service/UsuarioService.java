package com.estudo.loja.service;

import com.estudo.loja.dto.UsuarioDTO;
import com.estudo.loja.entity.Usuario;
import com.estudo.loja.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<UsuarioDTO> listar() {
        return repository.findAll()
                .stream()
                .map(UsuarioDTO::new)
                .toList();
    }

    public Usuario salvar(Usuario usuario) {
        return repository.save(usuario);
    }

    public Usuario atualizar(Long id, Usuario usuario) {
        Usuario usuarioBanco = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuarioBanco.setNome(usuario.getNome());
        usuarioBanco.setEmail(usuario.getEmail());
        usuarioBanco.setSenha(usuario.getSenha());

        return repository.save(usuarioBanco);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}