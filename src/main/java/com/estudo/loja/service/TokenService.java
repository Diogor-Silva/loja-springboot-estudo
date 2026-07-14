package com.estudo.loja.service;

import com.estudo.loja.entity.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenService {

    private final JwtEncoder jwtEncoder;
    private final long expiration;

    public TokenService(
            JwtEncoder jwtEncoder,
            @Value("${jwt.expiration}") long expiration
    ) {
        this.jwtEncoder = jwtEncoder;
        this.expiration = expiration;
    }

    public String gerarToken(Usuario usuario) {
        Instant agora = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("loja-api")
                .issuedAt(agora)
                .expiresAt(agora.plusSeconds(expiration))
                .subject(usuario.getEmail())
                .claim("usuarioId", usuario.getId())
                .claim("nome", usuario.getNome())
                .claim("perfil", usuario.getPerfil().name())
                .build();

        JwsHeader header = JwsHeader
                .with(MacAlgorithm.HS256)
                .build();

        return jwtEncoder.encode(
                JwtEncoderParameters.from(header, claims)
        ).getTokenValue();
    }
}