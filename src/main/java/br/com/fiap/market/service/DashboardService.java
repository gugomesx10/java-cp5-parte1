package br.com.fiap.market.service;

import br.com.fiap.market.dto.dashboard.DashboardResumo;
import br.com.fiap.market.entity.Compra;
import br.com.fiap.market.enums.StatusCompra;
import br.com.fiap.market.repository.CompraRepository;
import br.com.fiap.market.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DashboardService {

    private final CompraRepository compraRepository;
    private final ItemRepository itemRepository;

    public DashboardService(
            CompraRepository compraRepository,
            ItemRepository itemRepository
    ) {
        this.compraRepository = compraRepository;
        this.itemRepository = itemRepository;
    }

    public DashboardResumo obterResumo() {

        List<Compra> compras = compraRepository.findAll();

        long aprovadas = compras.stream()
                .filter(c -> c.getStatus() == StatusCompra.APROVADA)
                .count();

        long recusadas = compras.stream()
                .filter(c -> c.getStatus() == StatusCompra.RECUSADA)
                .count();

        long pendentes = compras.stream()
                .filter(c -> c.getStatus() == StatusCompra.PENDENTE)
                .count();

        BigDecimal totalAprovado = compras.stream()
                .filter(c -> c.getStatus() == StatusCompra.APROVADA)
                .map(Compra::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new DashboardResumo(
                compras.size(),
                aprovadas,
                recusadas,
                pendentes,
                totalAprovado,
                itemRepository.count()
        );
    }

    public List<Compra> listarComprasRecentes() {
        return compraRepository.findAll(
                PageRequest.of(
                        0,
                        10,
                        Sort.by(Sort.Direction.DESC, "criadaEm")
                )
        ).getContent();
    }
}