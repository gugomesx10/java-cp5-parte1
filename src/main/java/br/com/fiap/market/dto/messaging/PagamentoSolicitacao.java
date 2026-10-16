package br.com.fiap.market.dto.messaging;

import java.math.BigDecimal;

public record PagamentoSolicitacao(
        Long compraId,
        BigDecimal valor,
        String emailCliente
) {
}
