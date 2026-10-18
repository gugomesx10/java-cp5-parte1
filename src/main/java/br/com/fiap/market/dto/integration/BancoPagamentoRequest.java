package br.com.fiap.market.dto.integration;

import java.math.BigDecimal;

public record BancoPagamentoRequest(
        Long compraId,
        BigDecimal valor
) {
}