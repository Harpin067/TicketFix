package com.example.TicketFix.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
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
  public UserDetailsService users(PasswordEncoder encoder) {
    UserDetails admin = User.withUsername("admin")
      .password(encoder.encode("admin123"))
      .roles("ADMIN")
      .build();

    UserDetails tecnico = User.withUsername("tecnico")
      .password(encoder.encode("tecnico123"))
      .roles("TECNICO")
      .build();

    UserDetails cliente = User.withUsername("cliente")
      .password(encoder.encode("cliente123"))
      .roles("CLIENTE")
      .build();

    return new InMemoryUserDetailsManager(admin, tecnico, cliente);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
