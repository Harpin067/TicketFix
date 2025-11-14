package com.example.TicketFix.service;

import com.example.TicketFix.domain.Ticket;
import com.example.TicketFix.domain.Ticket.EstadoTicket;
import com.example.TicketFix.repo.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final TicketRepository repo;

    // record para agrupar estadísticas de manera limpia
    public record Stats(long total, long abiertos, long enProgreso, long cerrados) {}

    // ===== 1. Stats globales (ADMIN) =====
    public Stats statsGlobal() {
        return new Stats(
                repo.total(),
                repo.abiertos(),
                repo.enProgreso(),
                repo.cerrados()
        );
    }

    // ===== 2. Stats del TÉCNICO (usa campo tecnico.username) =====
    public Stats statsTecnico(String usernameTecnico) {
        long total      = repo.countByTecnico_Username(usernameTecnico);
        long abiertos   = repo.countByTecnico_UsernameAndEstado(usernameTecnico, EstadoTicket.ABIERTO);
        long enProgreso = repo.countByTecnico_UsernameAndEstado(usernameTecnico, EstadoTicket.EN_PROGRESO);
        long cerrados   = repo.countByTecnico_UsernameAndEstado(usernameTecnico, EstadoTicket.CERRADO);

        return new Stats(total, abiertos, enProgreso, cerrados);
    }

    // ===== 3. Stats del CREADOR (cliente) =====
    public Stats statsCreador(String usernameCreador) {
        long total      = repo.countByCreador_Username(usernameCreador);
        long abiertos   = repo.countByCreador_UsernameAndEstado(usernameCreador, EstadoTicket.ABIERTO);
        long enProgreso = repo.countByCreador_UsernameAndEstado(usernameCreador, EstadoTicket.EN_PROGRESO);
        long cerrados   = repo.countByCreador_UsernameAndEstado(usernameCreador, EstadoTicket.CERRADO);

        return new Stats(total, abiertos, enProgreso, cerrados);
    }

    // ===== 4. Últimos tickets globales (admin) =====
    public List<Ticket> ultimosGlobal(int n) {
        var sort = Sort.by(Sort.Direction.DESC, "fechaCreacion");
        return repo.findAll(PageRequest.of(0, n, sort)).getContent();
    }

    // ===== 5. Últimos tickets asignados a un TÉCNICO =====
    public List<Ticket> ultimosTecnico(String usernameTecnico, int n) {
        var sort = Sort.by(Sort.Direction.DESC, "fechaCreacion");
        return repo.findByTecnico_Username(usernameTecnico, PageRequest.of(0, n, sort))
                   .getContent();
    }

    // ===== 6. Últimos tickets creados por un USUARIO (cliente) =====
    public List<Ticket> ultimosCreador(String usernameCreador, int n) {
        var sort = Sort.by(Sort.Direction.DESC, "fechaCreacion");
        return repo.findByCreador_Username(usernameCreador, PageRequest.of(0, n, sort))
                   .getContent();
    }
}
