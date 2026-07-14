package com.estudo.loja.dto;

import com.estudo.loja.enums.PerfilUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {

    private String token;
    private String tipo;
    private Long usuarioId;
    private String nome;
    private String email;
    private PerfilUsuario perfil;
}