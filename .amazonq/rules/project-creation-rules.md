# Regla: Estructura de Proyecto Spring Boot

## Objetivo
Generar proyecos Spring Boot con una arquitectura en capas bien organizada siguiendo las mejores prácticas de Java.

## Estructura de Directorios

Todos los proyectos Spring Boot deben seguir esta estructura:
```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── example/
│   │           └── {project-name}/
│   │               ├── {ProjectName}Application.java
│   │               ├── config/
│   │               │   └── DataInitializer.java
│   │               ├── controller/
│   │               │   └── {Entity}Controller.java
│   │               ├── entity/
│   │               │   └── {Entity}.java
│   │               ├── exception/
│   │               │   ├── {Entity}NotFoundException.java
│   │               │   └── GlobalExceptionHandler.java
│   │               ├── repository/
│   │               │   └── {Entity}Repository.java
│   │               └── service/
│   │                   └── {Entity}Service.java
│   └── resources/
│       ├── application.properties
│       └── static/
└── test/
    └── java/
        └── com/
            └── example/
                └── {project-name}/
                    ├── controller/
                    │   └── {Entity}ControllerTest.java
                    └── service/
                        └── {Entity}ServiceTest.java
```

## Convenciones de Nomenclatura

- **Paquete base**: `com.example.{project-name}`
- **Clase principal**: `{ProjectName}Application.java`
- **Entidades**: Singular, PascalCase (ej: `Reservation.java`)
- **Controladores**: `{Entity}Controller.java`
- **Servicios**: `{Entity}Service.java`
- **Repositorios**: `{Entity}Repository.java`
- **Excepciones**: `{Entity}NotFoundException.java`

## Responsabilidades por Capa

### Controller
- Manejo de peticiones HTTP
- Validación de entrada
- Mapeo de DTOs
- Códigos de estado HTTP apropiados

### Service
- Lógica de negocio
- Transacciones
- Validaciones de negocio

### Repository
- Acceso a datos
- Operaciones CRUD
- Queries personalizadas

### Entity
- Modelo de dominio
- Anotaciones JPA
- Relaciones entre entidades

### Exception
- Excepciones personalizadas
- Manejador global de excepciones
- Respuestas de error estandarizadas

### Config
- Configuraciones de beans
- Inicialización de datos
- Configuraciones de seguridad

## Dependencias Estándar

Incluir en `pom.xml`:
- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- H2 Database (desarrollo)
- Lombok (opcional)
- Spring Boot Starter Test

## Configuración Base

`application.properties` debe incluir:
- Configuración de datasource
- Configuración de JPA/Hibernate
- Puerto del servidor
- Logging

## Tests

Cada capa debe tener sus tests correspondientes:
- `ControllerTest`: Tests de integración con MockMvc
- `ServiceTest`: Tests unitarios con Mockito