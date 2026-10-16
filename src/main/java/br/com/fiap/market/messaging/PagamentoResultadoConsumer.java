package br.com.fiap.market.messaging;

import br.com.fiap.market.config.RabbitMQConfig;
import br.com.fiap.market.dto.messaging.PagamentoResultado;
import br.com.fiap.market.service.CompraService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PagamentoResultadoConsumer {

    private final CompraService compraService;

    public PagamentoResultadoConsumer(CompraService compraService) {
        this.compraService = compraService;
    }

    @RabbitListener(
            queues = RabbitMQConfig.PAGAMENTO_RESULTADO_QUEUE
    )
    public void receber(PagamentoResultado resultado) {
        compraService.atualizarStatusPagamento(
                resultado.compraId(),
                resultado.status()
        );
    }
}