package com.estudo.loja.controller;

import com.estudo.loja.dto.CaixaRequest;
import com.estudo.loja.dto.ResumoDashboardDTO;
import com.estudo.loja.dto.VendaDTO;
import com.estudo.loja.entity.Caixa;
import com.estudo.loja.service.CaixaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    public Caixa finalizarVenda(
            @Valid @RequestBody CaixaRequest request
    ) {
        return service.finalizarVenda(request);
    }

    @GetMapping
    public List<VendaDTO> listar() {
        return service.listar();
    }

    @GetMapping("/resumo-dashboard")
    public ResumoDashboardDTO obterResumoDashboard() {
        return service.obterResumoDashboard();
    }
}