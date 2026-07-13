package com.estudo.loja.controller;


import com.estudo.loja.dto.CaixaRequest;
import com.estudo.loja.entity.Caixa;
import com.estudo.loja.service.CaixaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/caixas")
public class CaixaController {

    private final CaixaService service;

    public CaixaController(CaixaService service) {
        this.service = service;
    }

    @PostMapping
    public Caixa finalizarVenda(@RequestBody CaixaRequest request) {
        return service.finalizarVenda(request);
    }

    @GetMapping
    public List<Caixa> listar() {
        return service.listar();
    }
}