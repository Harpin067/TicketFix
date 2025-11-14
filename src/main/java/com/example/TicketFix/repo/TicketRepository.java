package com.example.TicketFix.repo;

import com.example.TicketFix.domain.Ticket;
import com.example.TicketFix.domain.Ticket.EstadoTicket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    // ===== estadísticas globales (admin) =====
    @Query("select count(t) from Ticket t")
    long total();

    @Query("select count(t) from Ticket t where t.estado = 'ABIERTO'")
    long abiertos();

    @Query("select count(t) from Ticket t where t.estado = 'EN_PROGRESO'")
    long enProgreso();

    @Query("select count(t) from Ticket t where t.estado = 'CERRADO'")
    long cerrados();

    // ===== por TÉCNICO (usando campo tecnico.username) =====
    long countByTecnico_Username(String username);
    long countByTecnico_UsernameAndEstado(String username, EstadoTicket estado);

    Page<Ticket> findByTecnico_Username(String username, Pageable pageable);

    // ===== por CREADOR (cliente) =====
    long countByCreador_Username(String username);
    long countByCreador_UsernameAndEstado(String username, EstadoTicket estado);

    Page<Ticket> findByCreador_Username(String username, Pageable pageable);
}
