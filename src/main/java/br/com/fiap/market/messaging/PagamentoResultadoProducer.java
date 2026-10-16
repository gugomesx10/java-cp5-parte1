package br.com.fiap.market.messaging;

import br.com.fiap.market.config.RabbitMQConfig;
import br.com.fiap.market.dto.messaging.PagamentoResultado;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class PagamentoResultadoProducer {

    private final RabbitTemplate rabbitTemplate;

    public PagamentoResultadoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviar(PagamentoResultado resultado) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.PAGAMENTO_EXCHANGE,
                RabbitMQConfig.PAGAMENTO_RESULTADO_ROUTING_KEY,
                resultado
        );
    }
}