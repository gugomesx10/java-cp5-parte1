package br.com.fiap.market.dto.messaging;

import br.com.fiap.market.enums.StatusPagamento;

import java.time.LocalDateTime;

public record PagamentoResultado(
        String transacaoId,
        Long compraId,
        StatusPagamento status,
        String mensagem,
        LocalDateTime processadoEm
) {
}