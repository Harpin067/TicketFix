package com.example.TicketFix.web;

import com.example.TicketFix.domain.Ticket;
import com.example.TicketFix.repo.TicketRepository;
import com.example.TicketFix.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/tecnico")
@RequiredArgsConstructor
public class TecnicoDashboardController {

    private final DashboardService dashboardService;
    private final TicketRepository ticketRepository;

    // GET /tecnico/dashboard → muestra stats y tickets asignados al técnico logueado
    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('TECNICO')")
    public String dashboardTecnico(Model model, Authentication authentication) {
        String username = authentication.getName();

        var stats = dashboardService.statsTecnico(username);
        model.addAttribute("stats", stats);
        model.addAttribute("ultimos", dashboardService.ultimosTecnico(username, 20));

        return "tecnico/dashboard";
    }

    // POST /tecnico/tickets/{id}/estado → actualizar estado de un ticket asignado a este técnico
    @PostMapping("/tickets/{id}/estado")
    @PreAuthorize("hasRole('TECNICO')")
    public String actualizarEstado(@PathVariable Long id,
                                   @RequestParam("estado") Ticket.EstadoTicket nuevoEstado,
                                   Authentication authentication,
                                   RedirectAttributes redirectAttributes) {

        String usernameTecnico = authentication.getName();

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket no encontrado"));

        // Validar que el ticket pertenece a este técnico
        if (ticket.getTecnico() == null ||
            ticket.getTecnico().getUsername() == null ||
            !ticket.getTecnico().getUsername().equals(usernameTecnico)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No puedes modificar este ticket");
        }

        ticket.setEstado(nuevoEstado);

        if (nuevoEstado == Ticket.EstadoTicket.CERRADO && ticket.getFechaCierre() == null) {
            ticket.setFechaCierre(LocalDateTime.now());
        }

        ticketRepository.save(ticket);

        redirectAttributes.addFlashAttribute("msg", "Estado del ticket actualizado correctamente.");
        return "redirect:/tecnico/dashboard";
    }
}
