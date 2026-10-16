package br.com.fiap.bancotranquiloapi.dto;

import java.math.BigDecimal;

public record PagamentoRequest(
        Long compraId,
        BigDecimal valor
) {
}
