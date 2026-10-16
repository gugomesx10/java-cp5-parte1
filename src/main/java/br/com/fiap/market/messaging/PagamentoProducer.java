package br.com.fiap.market.messaging;

import br.com.fiap.market.config.RabbitMQConfig;
import br.com.fiap.market.dto.messaging.PagamentoSolicitacao;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class PagamentoProducer {

    private final RabbitTemplate rabbitTemplate;

    public PagamentoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviar(PagamentoSolicitacao solicitacao) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.PAGAMENTO_EXCHANGE,
                RabbitMQConfig.PAGAMENTO_ROUTING_KEY,
                solicitacao
        );
    }
}