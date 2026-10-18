package br.com.fiap.market.service;

import br.com.fiap.market.entity.Compra;
import br.com.fiap.market.enums.StatusCompra;
import br.com.fiap.market.integration.BrevoEmailClient;
import br.com.fiap.market.repository.EmailEnvioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger log =
            LoggerFactory.getLogger(EmailService.class);

    private static final String ASSUNTO =
            "Market Express - Pagamento aprovado";

    private final EmailEnvioRepository emailEnvioRepository;
    private final BrevoEmailClient brevoEmailClient;

    public EmailService(
            EmailEnvioRepository emailEnvioRepository,
            BrevoEmailClient brevoEmailClient
    ) {
        this.emailEnvioRepository = emailEnvioRepository;
        this.brevoEmailClient = brevoEmailClient;
    }

    public void enviarConfirmacaoCompra(Compra compra) {

        if (compra.getStatus() != StatusCompra.APROVADA) {
            return;
        }

        boolean registrado = emailEnvioRepository.registrarPendente(
                compra.getId(),
                compra.getEmailCliente(),
                ASSUNTO
        );

        if (!registrado) {
            log.info("Envio já registrado para compra {}", compra.getId());
            return;
        }

        String conteudo = """
                Olá!

                Seu pagamento foi aprovado pelo Banco Tranquilo.

                Compra: #%d
                Valor: R$ %s

                Obrigado por comprar no Market Express!
                """.formatted(
                compra.getId(),
                compra.getValorTotal().toPlainString()
        );

        try {
            String messageId = brevoEmailClient.enviar(
                    compra.getEmailCliente(),
                    ASSUNTO,
                    conteudo
            );

            emailEnvioRepository.marcarEnviado(compra.getId());

            log.info(
                    "E-mail aceito pela Brevo. Compra: {}, MessageId: {}",
                    compra.getId(),
                    messageId
            );

        } catch (Exception e) {
            log.error(
                    "Falha no envio de e-mail da compra {}",
                    compra.getId(),
                    e
            );

            emailEnvioRepository.marcarFalha(
                    compra.getId(),
                    e.getMessage()
            );
        }
    }
}