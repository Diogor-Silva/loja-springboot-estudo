package com.estudo.loja.controller;

import com.estudo.loja.dto.LoginRequest;
import com.estudo.loja.dto.LoginResponse;
import com.estudo.loja.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public LoginResponse login(
            @Valid @RequestBody LoginRequest request
    ) {
        return service.login(request);
    }
}