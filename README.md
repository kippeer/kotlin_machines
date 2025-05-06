# Industrial Machine Monitoring API

A RESTful API for industrial machine monitoring and predictive maintenance built with Kotlin and Spring Boot.

## Features

- **Machine Management**: CRUD operations for industrial machines
- **Sensor Readings**: Collection and retrieval of machine sensor data
- **Anomaly Detection**: Automated alerts when readings exceed predefined thresholds
- **Operational Reports**: Comprehensive reports on uptime, failures, and energy consumption
- **Authentication & Authorization**: Secure API access with JWT

## Technologies

- **Language**: Kotlin
- **Framework**: Spring Boot
- **Database**: PostgreSQL (Production), H2 (Development)
- **Security**: Spring Security with JWT
- **Documentation**: OpenAPI/Swagger
- **Build Tool**: Maven

## API Endpoints

### Machine Management

- `POST /api/machines` - Register a new machine
- `GET /api/machines` - List all machines
- `GET /api/machines/{id}` - View machine details
- `PUT /api/machines/{id}` - Update machine data
- `DELETE /api/machines/{id}` - Remove machine

### Sensor Readings

- `POST /api/machines/{id}/readings` - Record sensor readings
- `GET /api/machines/{id}/readings` - Get reading history
- `GET /api/machines/{id}/readings/latest` - Get latest reading

### Alerts

- `GET /api/machines/{id}/alerts` - List all alerts
- `GET /api/machines/{id}/alerts/unresolved` - List active alerts
- `PUT /api/machines/{id}/alerts/{alertId}/resolve` - Resolve an alert

### Reports

- `GET /api/reports/uptime` - Machine uptime statistics
- `GET /api/reports/failures` - Machine failure statistics
- `GET /api/reports/energy` - Energy consumption statistics

### Authentication

- `POST /api/auth/login` - Authenticate user and get JWT token

## Getting Started

### Prerequisites

- JDK 17 or higher
- Maven
- PostgreSQL (for production)

### Development Setup

1. Clone the repository
2. Configure database in `application-dev.yml` for development
3. Run the application with the dev profile:
   ```
   ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
   ```
4. Access Swagger UI at `http://localhost:8080/api/swagger-ui.html`

### Production Setup

1. Configure environment variables:
   - `JDBC_DATABASE_URL`
   - `JDBC_DATABASE_USERNAME`
   - `JDBC_DATABASE_PASSWORD`
   - `JWT_SECRET`
   - `JWT_EXPIRATION`

2. Build the application:
   ```
   ./mvnw clean package -Pprod
   ```

3. Run the JAR file:
   ```
   java -jar target/monitoring-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
   ```