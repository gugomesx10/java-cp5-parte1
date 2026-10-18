package br.com.fiap.market.service;

import br.com.fiap.market.dto.messaging.PagamentoSolicitacao;
import br.com.fiap.market.entity.Compra;
import br.com.fiap.market.enums.StatusCompra;
import br.com.fiap.market.repository.CompraRepository;
import br.com.fiap.market.messaging.PagamentoProducer;
import org.springframework.stereotype.Service;
import br.com.fiap.market.enums.StatusPagamento;

import java.math.BigDecimal;
import java.util.List;


@Service
public class CompraService {

    private final CompraRepository compraRepository;
    private final PagamentoProducer pagamentoProducer;

    public CompraService(
            CompraRepository compraRepository,
            PagamentoProducer pagamentoProducer
    ) {
        this.compraRepository = compraRepository;
        this.pagamentoProducer = pagamentoProducer;
    }

    public Compra criarCompra(BigDecimal valorTotal, String emailCliente) {

        Compra compra = new Compra();
        compra.setValorTotal(valorTotal);
        compra.setEmailCliente(emailCliente);

        Compra compraSalva = compraRepository.save(compra);

        PagamentoSolicitacao solicitacao = new PagamentoSolicitacao(
                compraSalva.getId(),
                compraSalva.getValorTotal(),
                compraSalva.getEmailCliente()
        );

        pagamentoProducer.enviar(solicitacao);

        return compraSalva;
    }

    public List<Compra> listarTodas() {
        return compraRepository.findAll();
    }

    public Compra atualizarStatusPagamento(
            Long compraId,
            StatusPagamento statusPagamento
    ) {
        Compra compra = compraRepository.findById(compraId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Compra não encontrada: " + compraId
                        )
                );

        StatusCompra novoStatus = switch (statusPagamento) {
            case APROVADO -> StatusCompra.APROVADA;
            case RECUSADO -> StatusCompra.RECUSADA;
        };

        compra.setStatus(novoStatus);

        return compraRepository.save(compra);
    }
}