package br.com.fiap.market.dto;

import java.math.BigDecimal;

public record CompraRequest(
        BigDecimal valorTotal,
        String emailCliente
) {
}