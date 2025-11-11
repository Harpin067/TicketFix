package com.example.TicketFix.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardRedirectController {

    @GetMapping("/dashboard")
    public String redirectToDashboard(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                String role = authority.getAuthority();
                if (role.contains("ADMIN")) {
                    return "redirect:/admin/dashboard";
                } else if (role.contains("TECNICO")) {
                    return "redirect:/tecnico/dashboard";
                } else if (role.contains("CLIENTE")) {
                    return "redirect:/cliente/dashboard";
                }
            }
        }
        // Si no hay autenticación o rol, redirige a login
        return "redirect:/login";
    }

    @GetMapping("/")
    public String home(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/dashboard";
        }
        return "redirect:/login";
    }
}
