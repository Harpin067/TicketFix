package com.example.TicketFix.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comentarios_tickets")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ComentarioTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @ManyToOne @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(columnDefinition = "TEXT")
    private String comentario;

    @Builder.Default
    private LocalDateTime fechaComentario = LocalDateTime.now();
}
