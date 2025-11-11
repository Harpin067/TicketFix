package com.example.TicketFix.config;

import com.example.TicketFix.domain.Usuario;
import com.example.TicketFix.repo.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

  @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/css/**", "/js/**", "/images/**", "/", "/login", "/register", "/h2-console/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/dashboard", true)
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                // CSRF enabled for POST /login (token must be included in form)
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")
                )
                // Allow frames for H2 console (if used) - use new method to avoid deprecation
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                );

        return http.build();
    }

  @Bean
        public UserDetailsService userDetailsService(UsuarioRepository usuarioRepository) {
                return username -> {
                        Usuario usuario = usuarioRepository.findByUsername(username)
                                                        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

                        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                        if (usuario.getRol() != null && usuario.getRol().getNombre() != null) {
                                String roleName = usuario.getRol().getNombre();
                                if (!roleName.startsWith("ROLE_")) {
                                        roleName = "ROLE_" + roleName;
                                }
                                authorities.add(new SimpleGrantedAuthority(roleName));
                        }

                        return User
                                                        .withUsername(usuario.getUsername())
                                                        .password(usuario.getPassword())
                                                        .authorities(authorities)
                                                        .accountExpired(false)
                                                        .accountLocked(false)
                                                        .credentialsExpired(false)
                                                        .disabled(!Boolean.TRUE.equals(usuario.getActivo()))
                                                        .build();
                };
        }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
