package com.example.TicketFix.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EstadoTicket estado = EstadoTicket.ABIERTO;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Prioridad prioridad = Prioridad.MEDIA;

    @ManyToOne
    @JoinColumn(name = "creador_id")
    private Usuario creador;

    @ManyToOne
    @JoinColumn(name = "tecnico_id")
    private Usuario tecnico;

    @Builder.Default
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    private LocalDateTime fechaCierre;

    public enum EstadoTicket { ABIERTO, EN_PROGRESO, CERRADO }
    public enum Prioridad { ALTA, MEDIA, BAJA }
}
