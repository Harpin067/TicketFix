package com.example.TicketFix.config;

import com.example.TicketFix.domain.Rol;
import com.example.TicketFix.domain.Usuario;
import com.example.TicketFix.repo.RolRepository;
import com.example.TicketFix.repo.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Crear roles si no existen
        if (rolRepository.count() == 0) {
            Rol adminRole = Rol.builder().nombre("ADMIN").build();
            Rol tecnicoRole = Rol.builder().nombre("TECNICO").build();
            Rol clienteRole = Rol.builder().nombre("CLIENTE").build();

            rolRepository.save(adminRole);
            rolRepository.save(tecnicoRole);
            rolRepository.save(clienteRole);
        }

        // Crear usuarios de prueba si no existen
        if (usuarioRepository.count() == 0) {
            Rol adminRole = rolRepository.findAll().stream()
                    .filter(r -> r.getNombre().equals("ADMIN"))
                    .findFirst()
                    .orElseThrow();
            Rol tecnicoRole = rolRepository.findAll().stream()
                    .filter(r -> r.getNombre().equals("TECNICO"))
                    .findFirst()
                    .orElseThrow();
            Rol clienteRole = rolRepository.findAll().stream()
                    .filter(r -> r.getNombre().equals("CLIENTE"))
                    .findFirst()
                    .orElseThrow();

            // Admin
            Usuario admin = Usuario.builder()
                    .nombre("Administrador")
                    .correo("admin@ticketfix.local")
                    .password(passwordEncoder.encode("admin123"))
                    .rol(adminRole)
                    .activo(true)
                    .fechaRegistro(LocalDateTime.now())
                    .build();

            // Técnico
            Usuario tecnico = Usuario.builder()
                    .nombre("Técnico Soporte")
                    .correo("tecnico@ticketfix.local")
                    .password(passwordEncoder.encode("tecnico123"))
                    .rol(tecnicoRole)
                    .activo(true)
                    .fechaRegistro(LocalDateTime.now())
                    .build();

            // Cliente
            Usuario cliente = Usuario.builder()
                    .nombre("Cliente Test")
                    .correo("cliente@ticketfix.local")
                    .password(passwordEncoder.encode("cliente123"))
                    .rol(clienteRole)
                    .activo(true)
                    .fechaRegistro(LocalDateTime.now())
                    .build();

            usuarioRepository.save(admin);
            usuarioRepository.save(tecnico);
            usuarioRepository.save(cliente);

            System.out.println("✅ Base de datos inicializada con usuarios de prueba");
            System.out.println("   Admin: admin@ticketfix.local / admin123");
            System.out.println("   Técnico: tecnico@ticketfix.local / tecnico123");
            System.out.println("   Cliente: cliente@ticketfix.local / cliente123");
        }
    }
}
