package br.com.fiap.market.entity;

import br.com.fiap.market.enums.StatusCompra;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "TDS_TB_COMPRA")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @Column(nullable = false)
    private String emailCliente;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusCompra status;

    @Column(nullable = false)
    private LocalDateTime criadaEm;

    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = StatusCompra.PENDENTE;
        }

        if (criadaEm == null) {
            criadaEm = LocalDateTime.now();
        }
    }
}
