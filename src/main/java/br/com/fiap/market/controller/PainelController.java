package br.com.fiap.market.controller;

import br.com.fiap.market.service.DashboardService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PainelController {

    private final DashboardService dashboardService;

    public PainelController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/painel")
    public String painel() {
        return "painel";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/dashboard")
    public String dashboardAdmin(Model model) {

        model.addAttribute(
                "resumo",
                dashboardService.obterResumo()
        );
        model.addAttribute(
                "compras",
                dashboardService.listarComprasRecentes()
        );

        return "admin-dashboard";
    }
}