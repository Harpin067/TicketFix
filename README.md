---
                                                                                
  # TicketFix
                                                                                
  Sistema de gestión de tickets de soporte técnico construido con Spring Boot y 
  MySQL, desplegable con Docker Compose.                                      
                                                                                
  ## Tecnologías                               
                                                                              
  - **Java 21** + **Spring Boot 3.5.4**                                         
  - **Spring Security** — autenticación y autorización por roles
  - **Thymeleaf** — plantillas HTML del lado del servidor                       
  - **Spring Data JPA / Hibernate** — capa de persistencia                      
  - **Flyway** — migraciones de base de datos                                 
  - **MySQL 8.0** — base de datos relacional                                    
  - **Lombok** — reducción de boilerplate                                       
  - **Docker Compose** — orquestación de contenedores                           
                                                                                
  ## Modelo de dominio                                                          
                                                                              
  | Entidad            | Descripción                                      |   
  |--------------------|--------------------------------------------------|
  | `Usuario`          | Usuarios del sistema con rol asignado            |     
  | `Rol`              | Roles (p.ej. ADMIN, TÉCNICO, USUARIO)            |
  | `Ticket`           | Solicitud de soporte con estado y prioridad      |     
  | `HistorialTicket`  | Registro de cambios de estado de un ticket       |     
  | `Notificacion`     | Notificaciones generadas por eventos del sistema |     
                                                                                
  ### Estados de ticket                                                       
  `ABIERTO` → `EN_PROGRESO` → `CERRADO`                                         
                                               
  ### Prioridades                                                               
  `ALTA` | `MEDIA` | `BAJA`                    
                           
  ## Requisitos
               
  - Docker y Docker Compose instalados
                                                                                
  ## Levantar el proyecto
                                                                                
  ```bash                                      
  docker compose up --build
                           
  La aplicación estará disponible en: http://localhost:8081
                                                                                
  Credenciales de desarrollo
                                                                                
  ┌─────────┬────────────┐                     
  │ Usuario │ Contraseña │
  ├─────────┼────────────┤
  │ admin   │ admin123   │
  └─────────┴────────────┘
                                                                                
  Estructura del proyecto
                                                                                
  src/                                         
  ├── main/
  │   ├── java/com/example/TicketFix/
  │   │   ├── config/       # Configuración de seguridad
  │   │   ├── domain/       # Entidades JPA                                     
  │   │   ├── repo/         # Repositorios Spring Data
  │   │   ├── service/      # Lógica de negocio                                 
  │   │   └── web/          # Controladores MVC
  │   └── resources/                                                            
  │       ├── templates/    # Vistas Thymeleaf                                  
  │       └── application.properties
                                                                                
  Variables de entorno (Docker)                                                 
   
  ┌───────────────────────────────┬────────────────────────────────────┐        
  │           Variable            │         Valor por defecto          │
  ├───────────────────────────────┼────────────────────────────────────┤        
  │ SPRING_PROFILES_ACTIVE        │ docker                             │
  ├───────────────────────────────┼────────────────────────────────────┤
  │ SPRING_DATASOURCE_URL         │ jdbc:mysql://db:3306/ticketfix?... │
  ├───────────────────────────────┼────────────────────────────────────┤        
  │ SPRING_DATASOURCE_USERNAME    │ ticketfix                          │
  ├───────────────────────────────┼────────────────────────────────────┤        
  │ SPRING_DATASOURCE_PASSWORD    │ ticketfix                          │
  ├───────────────────────────────┼────────────────────────────────────┤
  │ SPRING_SECURITY_USER_NAME     │ admin                              │
  ├───────────────────────────────┼────────────────────────────────────┤        
  │ SPRING_SECURITY_USER_PASSWORD │ admin123                           │
  └───────────────────────────────┴────────────────────────────────────┘        
                                               
  ▎ Las credenciales de la tabla anterior son solo para desarrollo local.       
  ▎ Cámbialas antes de cualquier despliegue en producción.
