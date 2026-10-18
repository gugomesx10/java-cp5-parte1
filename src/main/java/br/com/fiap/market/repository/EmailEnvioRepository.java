package br.com.fiap.market.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EmailEnvioRepository {

    private final JdbcTemplate jdbcTemplate;

    public EmailEnvioRepository(
            @Qualifier("emailJdbcTemplate") JdbcTemplate jdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean registrarPendente(
            Long compraId,
            String destinatario,
            String assunto
    ) {
        int linhas = jdbcTemplate.update("""
                INSERT INTO email_envio
                    (compra_id, destinatario, assunto, status)
                VALUES (?, ?, ?, 'PENDENTE')
                ON CONFLICT (compra_id) DO NOTHING
                """,
                compraId,
                destinatario,
                assunto
        );

        return linhas == 1;
    }

    public boolean marcarEnviado(Long compraId) {
        int linhas = jdbcTemplate.update("""
            UPDATE email_envio
            SET status = 'ENVIADO',
                enviado_em = CURRENT_TIMESTAMP,
                tentativas = tentativas + 1,
                erro = NULL
            WHERE compra_id = ?
              AND status <> 'ENVIADO'
            """,
                compraId
        );

        return linhas == 1;
    }

    public boolean marcarFalha(Long compraId, String mensagemErro) {
        int linhas = jdbcTemplate.update("""
            UPDATE email_envio
            SET status = 'FALHA',
                tentativas = tentativas + 1,
                erro = ?
            WHERE compra_id = ?
              AND status <> 'ENVIADO'
            """,
                mensagemErro,
                compraId
        );

        return linhas == 1;
    }
}