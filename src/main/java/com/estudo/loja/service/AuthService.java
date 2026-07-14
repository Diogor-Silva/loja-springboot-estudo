package com.estudo.loja.service;

import com.estudo.loja.dto.LoginRequest;
import com.estudo.loja.dto.LoginResponse;
import com.estudo.loja.entity.Usuario;
import com.estudo.loja.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            TokenService tokenService
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "E-mail ou senha inválidos"
                ));

        boolean senhaCorreta = passwordEncoder.matches(
                request.getSenha(),
                usuario.getSenha()
        );

        if (!senhaCorreta) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "E-mail ou senha inválidos"
            );
        }

        String token = tokenService.gerarToken(usuario);

        return new LoginResponse(
                token,
                "Bearer",
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPerfil()
        );
    }
}