package br.com.fiap.bancotranquiloapi.service;

import br.com.fiap.bancotranquiloapi.dto.PagamentoRequest;
import br.com.fiap.bancotranquiloapi.dto.PagamentoResponse;
import br.com.fiap.bancotranquiloapi.enums.StatusPagamento;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PagamentoService {

    private static final BigDecimal LIMITE_APROVACAO =
            new BigDecimal("1000.00");

    public PagamentoResponse processar(PagamentoRequest request) {

        StatusPagamento status;

        if (request.valor().compareTo(LIMITE_APROVACAO) <= 0) {
            status = StatusPagamento.APROVADO;
        } else {
            status = StatusPagamento.RECUSADO;
        }

        String mensagem = status == StatusPagamento.APROVADO
                ? "Pagamento aprovado pelo Banco Tranquilo"
                : "Pagamento recusado: valor acima do limite permitido";

        return new PagamentoResponse(
                UUID.randomUUID().toString(),
                request.compraId(),
                status,
                mensagem,
                LocalDateTime.now()
        );
    }
}