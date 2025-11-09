package com.example.TicketFix.repo;

import com.example.TicketFix.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("select count(t) from Ticket t")
    long total();

    @Query("select count(t) from Ticket t where t.estado = 'ABIERTO'")
    long abiertos();

    @Query("select count(t) from Ticket t where t.estado = 'EN_PROGRESO'")
    long enProgreso();

    @Query("select count(t) from Ticket t where t.estado = 'CERRADO'")
    long cerrados();
}
