package com.example.TicketFix.service;

import com.example.TicketFix.domain.Ticket;
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

    public Stats stats() {
        return new Stats(repo.total(), repo.abiertos(), repo.enProgreso(), repo.cerrados());
    }

    public List<Ticket> ultimos(int n) {
        var sort = Sort.by(Sort.Direction.DESC, "fechaCreacion");
        return repo.findAll(PageRequest.of(0, n, sort)).getContent();
    }
}
