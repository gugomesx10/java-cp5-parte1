package br.com.fiap.market.controller;

import br.com.fiap.market.dto.CompraRequest;
import br.com.fiap.market.entity.Compra;
import br.com.fiap.market.service.CompraService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compras")
public class CompraController {

    private final CompraService compraService;

    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @PostMapping
    public ResponseEntity<Compra> criar(@RequestBody CompraRequest request) {

        Compra compra = compraService.criarCompra(
                request.valorTotal(),
                request.emailCliente()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(compra);
    }

    @GetMapping
    public ResponseEntity<List<Compra>> listarTodas() {
        return ResponseEntity.ok(compraService.listarTodas());
    }
}
