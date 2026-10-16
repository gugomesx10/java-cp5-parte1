package br.com.fiap.market.integration;

import br.com.fiap.market.dto.integration.BancoPagamentoRequest;
import br.com.fiap.market.dto.integration.BancoPagamentoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class BancoTranquiloClient {

    private final RestClient restClient;

    public BancoTranquiloClient(
            RestClient.Builder builder,
            @Value("${integracao.banco-tranquilo.base-url}") String baseUrl
    ) {
        this.restClient = builder
                .baseUrl(baseUrl)
                .build();
    }

    public BancoPagamentoResponse processar(BancoPagamentoRequest request) {
        return restClient
                .post()
                .uri("/api/pagamentos")
                .body(request)
                .retrieve()
                .body(BancoPagamentoResponse.class);
    }
}