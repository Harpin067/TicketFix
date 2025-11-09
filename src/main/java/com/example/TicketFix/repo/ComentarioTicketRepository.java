package com.example.TicketFix.repo;

import com.example.TicketFix.domain.ComentarioTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentarioTicketRepository extends JpaRepository<ComentarioTicket, Long> {
}
