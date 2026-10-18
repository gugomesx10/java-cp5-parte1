package br.com.fiap.market.messaging;

import br.com.fiap.market.config.RabbitMQConfig;
import br.com.fiap.market.dto.messaging.PagamentoResultado;
import br.com.fiap.market.entity.Compra;
import br.com.fiap.market.enums.StatusCompra;
import br.com.fiap.market.service.CompraService;
import br.com.fiap.market.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PagamentoResultadoConsumer {

    private final CompraService compraService;
    private final EmailService emailService;

    public PagamentoResultadoConsumer(
            CompraService compraService,
            EmailService emailService
    ) {
        this.compraService = compraService;
        this.emailService = emailService;
    }

    @RabbitListener(queues = RabbitMQConfig.PAGAMENTO_RESULTADO_QUEUE)
    public void receber(PagamentoResultado resultado) {

        Compra compra = compraService.atualizarStatusPagamento(
                resultado.compraId(),
                resultado.status()
        );

        if (compra.getStatus() == StatusCompra.APROVADA) {
            emailService.enviarConfirmacaoCompra(compra);
        }
    }
}