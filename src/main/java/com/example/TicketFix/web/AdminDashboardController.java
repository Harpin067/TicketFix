package com.example.TicketFix.web;

import com.example.TicketFix.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminDashboardController {

    private final DashboardService service;

    @GetMapping("/admin/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminDashboard(Model model) {
        var stats = service.stats();
        model.addAttribute("stats", stats);
        model.addAttribute("ultimos", service.ultimos(10));
        return "admin/dashboard";
    }

    @GetMapping("/cliente/dashboard")
    @PreAuthorize("hasRole('CLIENTE')")
    public String clienteDashboard(Model model) {
        var stats = service.stats();
        model.addAttribute("stats", stats);
        model.addAttribute("ultimos", service.ultimos(10));
        return "cliente/dashboard";
    }

    @GetMapping("/tecnico/dashboard")
    @PreAuthorize("hasRole('TECNICO')")
    public String tecnicoDashboard(Model model) {
        var stats = service.stats();
        model.addAttribute("stats", stats);
        model.addAttribute("ultimos", service.ultimos(10));
        return "tecnico/dashboard";
    }
}
