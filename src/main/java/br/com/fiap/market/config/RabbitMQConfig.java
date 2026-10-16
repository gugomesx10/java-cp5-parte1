package br.com.fiap.market.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;


@Configuration
public class RabbitMQConfig {
    public static final String PAGAMENTO_QUEUE = "market.pagamentos.queue";

    public static final String PAGAMENTO_EXCHANGE = "market.pagamentos.exchange";

    public static final String PAGAMENTO_ROUTING_KEY = "market.pagamentos.processar";

    public static final String PAGAMENTO_RESULTADO_QUEUE = "market.pagamentos.resultado.queue";

    public static final String PAGAMENTO_RESULTADO_ROUTING_KEY = "market.pagamentos.resultado";

    @Bean
    public Queue pagamentoQueue() {
        return new Queue(PAGAMENTO_QUEUE, true);
    }

    @Bean
    public DirectExchange pagamentoExchange() {
        return new DirectExchange(PAGAMENTO_EXCHANGE);
    }

    @Bean
    public Binding pagamentoBinding() {
        return BindingBuilder
                .bind(pagamentoQueue())
                .to(pagamentoExchange())
                .with(PAGAMENTO_ROUTING_KEY);
    }

    @Bean
    public JacksonJsonMessageConverter jsonMessageConverter(){
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public Queue pagamentoResultadoQueue() {
        return new Queue(PAGAMENTO_RESULTADO_QUEUE, true);
    }

    @Bean
    public Binding pagamentoResultadoBinding() {
        return BindingBuilder
                .bind(pagamentoResultadoQueue())
                .to(pagamentoExchange())
                .with(PAGAMENTO_RESULTADO_ROUTING_KEY);
    }
}
