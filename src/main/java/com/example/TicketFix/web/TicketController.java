package com.example.TicketFix.web;

import com.example.TicketFix.domain.Ticket;
import com.example.TicketFix.repo.TicketRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final TicketRepository repo;

    public TicketController(TicketRepository repo) { this.repo = repo; }

    // Formulario (ya no necesitamos Model si la vista es simple)
    @GetMapping("/nuevo")
    public String nuevo() {
        return "tickets/form"; // src/main/resources/templates/tickets/form.html
    }

    // Guardar desde el form simple (name=... en inputs)
    @PostMapping
    public String guardar(
            @RequestParam String titulo,
            @RequestParam String descripcion,
            @RequestParam(defaultValue = "MEDIA") Ticket.Prioridad prioridad,
            @RequestParam(defaultValue = "ABIERTO") Ticket.EstadoTicket estado,
            @RequestParam(required = false) String empresa,       // si lo tienes en el form
            @RequestParam(required = false, name = "asignadoA") String asignadoA, // si lo tienes en el form
            RedirectAttributes ra
    ) {
        Ticket t = Ticket.builder()
                .titulo(titulo)
                .descripcion(descripcion)
                .prioridad(prioridad)
                .estado(estado)
                // creador y tecnico quedan null por ahora (no hay selección en el form)
                .build();

        // si tu entidad no tiene empresa/asignadoA, ignora estas líneas;
        // si sí las tienes, añade los campos a la entidad y mapea la columna:
        //   @Column(name="asignado_a") private String asignadoA;
        //   private String empresa;
        // t.setEmpresa(empresa);
        // t.setAsignadoA(asignadoA);

        repo.save(t);
        ra.addFlashAttribute("ok", "Ticket creado: " + t.getTitulo());
        return "redirect:/admin/dashboard";
    }

    // (opcional) listado simple
    @GetMapping
    public String listar(org.springframework.ui.Model model) {
        model.addAttribute("tickets", repo.findAll());
        return "tickets/lista";
    }
}
