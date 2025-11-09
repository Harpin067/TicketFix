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
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/", "/login", "/css/**", "/js/**", "/images/**").permitAll()
        .requestMatchers("/admin/**").hasRole("ADMIN")
        .requestMatchers("/tecnico/**").hasAnyRole("TECNICO","ADMIN")
        .requestMatchers("/cliente/**").hasAnyRole("CLIENTE","ADMIN")
        .requestMatchers("/tickets/**").authenticated()
        .anyRequest().authenticated()
      )
      .formLogin(form -> form
        .loginPage("/login")
        .failureUrl("/login?error=true")
        .defaultSuccessUrl("/admin/dashboard", true)
        .permitAll()
      )
      .logout(logout -> logout
        .logoutUrl("/logout")
        .logoutSuccessUrl("/login?logout=true")
        .permitAll()
      )
      .httpBasic(Customizer.withDefaults());
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
