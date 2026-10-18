package br.com.fiap.bancotranquiloapi.controller;

import br.com.fiap.bancotranquiloapi.dto.PagamentoRequest;
import br.com.fiap.bancotranquiloapi.dto.PagamentoResponse;
import br.com.fiap.bancotranquiloapi.service.PagamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping
    public ResponseEntity<PagamentoResponse> processar(
            @RequestBody PagamentoRequest request
    ) {
        return ResponseEntity.ok(
                pagamentoService.processar(request)
        );
    }
}