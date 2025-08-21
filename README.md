# Customer Service

Este proyecto es un microservicio desarrollado en Spring Boot para la gestión de clientes, utilizando una arquitectura moderna basada en contenedores y servicios externos.

## Despliegue y entorno

El entorno se levanta fácilmente utilizando Docker y Docker Compose, permitiendo iniciar todos los servicios necesarios con un solo comando.

### start.sh
El script `start.sh` automatiza el proceso de despliegue:
- Detiene cualquier instancia previa de los contenedores.
- Levanta los servicios de PostgreSQL, RabbitMQ, Keycloak y la aplicación `customer-service`.
- Espera a que cada servicio esté disponible antes de continuar, asegurando que el entorno esté listo para trabajar.

### docker-compose.yml
El archivo `docker-compose.yml` define los siguientes servicios:
- **Postgres**: Base de datos principal.
- **RabbitMQ**: Sistema de mensajería.
- **Keycloak**: Gestión de autenticación y autorización.
- **customer-service**: Microservicio principal de la aplicación.

Cada servicio está configurado con variables de entorno, puertos y chequeos de salud para garantizar su correcto funcionamiento.

## Uso rápido

```bash
./start.sh
```

Esto levantará todo el entorno necesario para desarrollo y pruebas.

---

Para más detalles sobre endpoints, configuración y desarrollo, consulta las siguientes secciones.
