package br.com.fiap.market.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmailSchemaInitializer implements ApplicationRunner {

    private final JdbcTemplate jdbcTemplate;

    public EmailSchemaInitializer(
            @Qualifier("emailJdbcTemplate") JdbcTemplate jdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(ApplicationArguments args) {

        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS email_envio (
                    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                    compra_id BIGINT NOT NULL UNIQUE,
                    destinatario VARCHAR(254) NOT NULL,
                    assunto VARCHAR(200) NOT NULL,
                    status VARCHAR(20) NOT NULL DEFAULT 'PENDENTE'
                        CHECK (status IN ('PENDENTE', 'ENVIADO', 'FALHA')),
                    tentativas INTEGER NOT NULL DEFAULT 0,
                    criado_em TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                    enviado_em TIMESTAMPTZ,
                    erro TEXT
                )
                """);

        System.out.println("Tabela email_envio pronta no PostgreSQL.");
    }
}