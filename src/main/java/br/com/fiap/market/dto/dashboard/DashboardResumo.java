package br.com.fiap.market.dto.dashboard;

import java.math.BigDecimal;

public record DashboardResumo(
        long totalCompras,
        long comprasAprovadas,
        long comprasRecusadas,
        long comprasPendentes,
        BigDecimal valorTotalAprovado,
        long totalProdutos
) {
}