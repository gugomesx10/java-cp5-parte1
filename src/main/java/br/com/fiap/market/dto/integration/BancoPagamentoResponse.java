package br.com.fiap.market.dto.integration;

import br.com.fiap.market.enums.StatusPagamento;

import java.time.LocalDateTime;

public record BancoPagamentoResponse(
        String transacaoId,
        Long compraId,
        StatusPagamento status,
        String mensagem,
        LocalDateTime processadoEm
) {
}