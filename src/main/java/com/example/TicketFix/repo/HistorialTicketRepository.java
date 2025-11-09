package com.example.TicketFix.repo;

import com.example.TicketFix.domain.HistorialTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorialTicketRepository extends JpaRepository<HistorialTicket, Long> {
}
