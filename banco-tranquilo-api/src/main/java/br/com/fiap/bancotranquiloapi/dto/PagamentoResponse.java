package br.com.fiap.bancotranquiloapi.dto;

import br.com.fiap.bancotranquiloapi.enums.StatusPagamento;

import java.time.LocalDateTime;

public record PagamentoResponse(
        String transacaoId,
        Long compraId,
        StatusPagamento status,
        String mensagem,
        LocalDateTime processadoEm
) {
}