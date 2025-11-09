package com.example.TicketFix.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "historial_tickets")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class HistorialTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @ManyToOne @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String accion;
    private String estadoAnterior;
    private String estadoNuevo;

    @Builder.Default
    private LocalDateTime fecha = LocalDateTime.now();
}
