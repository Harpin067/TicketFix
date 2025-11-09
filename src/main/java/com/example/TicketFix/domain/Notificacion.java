package com.example.TicketFix.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String mensaje;
    @Builder.Default
    private Boolean leida = false;

    @Builder.Default
    private LocalDateTime fecha = LocalDateTime.now();
}
