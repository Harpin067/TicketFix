package com.example.TicketFix.web;

import com.example.TicketFix.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminDashboardController {

    private final DashboardService service;

    // DASHBOARD ADMIN: estadísticas y tickets globales
    @GetMapping("/admin/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminDashboard(Model model) {
        var stats = service.statsGlobal();        // <- global
        model.addAttribute("stats", stats);
        model.addAttribute("ultimos", service.ultimosGlobal(10));
        return "admin/dashboard";
    }

    // DASHBOARD CLIENTE: solo tickets donde él es "creador"
    @GetMapping("/cliente/dashboard")
    @PreAuthorize("hasRole('CLIENTE')")
    public String clienteDashboard(Model model, Authentication authentication) {
        String username = authentication.getName();
        var stats = service.statsCreador(username);         // <- por creador
        model.addAttribute("stats", stats);
        model.addAttribute("ultimos", service.ultimosCreador(username, 10));
        return "cliente/dashboard";
    }

  
}
