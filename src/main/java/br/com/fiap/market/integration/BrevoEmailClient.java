package br.com.fiap.market.integration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class BrevoEmailClient {

    private final RestClient restClient;
    private final String remetente;

    public BrevoEmailClient(
            RestClient.Builder builder,
            @Value("${integracao.brevo.api-key}") String apiKey,
            @Value("${integracao.brevo.remetente}") String remetente
    ) {
        this.restClient = builder
                .baseUrl("https://api.brevo.com/v3")
                .defaultHeader("api-key", apiKey)
                .build();

        this.remetente = remetente;
    }

    public String enviar(
            String destinatario,
            String assunto,
            String conteudo
    ) {
        BrevoRequest request = new BrevoRequest(
                new Remetente("Market Express", remetente),
                List.of(new Destinatario(destinatario)),
                assunto,
                conteudo
        );

        BrevoResponse response = restClient
                .post()
                .uri("/smtp/email")
                .body(request)
                .retrieve()
                .body(BrevoResponse.class);

        if (response == null || response.messageId() == null) {
            throw new IllegalStateException(
                    "A Brevo não retornou o identificador da mensagem."
            );
        }

        return response.messageId();
    }

    private record Remetente(
            String name,
            String email
    ) {}

    private record Destinatario(
            String email
    ) {}

    private record BrevoRequest(
            Remetente sender,
            List<Destinatario> to,
            String subject,
            String textContent
    ) {}

    private record BrevoResponse(
            String messageId
    ) {}
}