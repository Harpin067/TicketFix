package com.example.TicketFix.web;

import com.example.TicketFix.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminDashboardController {

    private final DashboardService service;

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        var stats = service.stats();
        model.addAttribute("stats", stats);
        model.addAttribute("ultimos", service.ultimos(10));
        return "admin/dashboard";
    }
}
