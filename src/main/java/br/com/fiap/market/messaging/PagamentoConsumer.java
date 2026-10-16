package br.com.fiap.market.messaging;

import br.com.fiap.market.config.RabbitMQConfig;
import br.com.fiap.market.dto.integration.BancoPagamentoRequest;
import br.com.fiap.market.dto.integration.BancoPagamentoResponse;
import br.com.fiap.market.dto.messaging.PagamentoResultado;
import br.com.fiap.market.dto.messaging.PagamentoSolicitacao;
import br.com.fiap.market.integration.BancoTranquiloClient;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PagamentoConsumer {

    private final BancoTranquiloClient bancoTranquiloClient;
    private final PagamentoResultadoProducer resultadoProducer;

    public PagamentoConsumer(
            BancoTranquiloClient bancoTranquiloClient,
            PagamentoResultadoProducer resultadoProducer
    ) {
        this.bancoTranquiloClient = bancoTranquiloClient;
        this.resultadoProducer = resultadoProducer;
    }


    @RabbitListener(queues = RabbitMQConfig.PAGAMENTO_QUEUE)
    public void receber(PagamentoSolicitacao solicitacao) {

        BancoPagamentoRequest request = new BancoPagamentoRequest(
                solicitacao.compraId(),
                solicitacao.valor()
        );

        BancoPagamentoResponse response =
                bancoTranquiloClient.processar(request);

        PagamentoResultado resultado = new PagamentoResultado(
                response.transacaoId(),
                response.compraId(),
                response.status(),
                response.mensagem(),
                response.processadoEm()
        );

        resultadoProducer.enviar(resultado);
    }
}