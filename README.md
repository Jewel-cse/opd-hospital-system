# OPD Hospital System

A **microservices-based Outpatient Department (OPD) Hospital Management System** built with Spring Boot 4.0.0 and Java 21.

---

## 🏗️ Architecture

```
┌──────────────────────────────────────────────────────────────┐
│                        API Gateway                           │
│                     (opd-api-gateway)                        │
│                        Port: 8080                            │
└─────────────────────────┬────────────────────────────────────┘
                          │
              ┌───────────┼───────────────────────────┐
              │           │                           │
              ▼           ▼                           ▼
┌─────────────────┐ ┌─────────────────┐ ┌─────────────────────┐
│  Auth Service   │ │ Doctor Service  │ │ Appointment Service │
│   Port: 8081    │ │   Port: 8082    │ │    Port: 8083       │
│   PostgreSQL    │ │   PostgreSQL    │ │    PostgreSQL       │
└─────────────────┘ └─────────────────┘ └─────────────────────┘
              │           │                           │
              └───────────┼───────────────────────────┘
                          │
              ┌───────────┴───────────────────────────┐
              │                                       │
              ▼                                       ▼
┌─────────────────────┐               ┌─────────────────────────┐
│  Billing Service    │               │  Notification Service   │
│    Port: 8084       │               │      Port: 8085         │
│     MongoDB         │               │      (No DB)            │
└─────────────────────┘               └─────────────────────────┘
                          │
                          ▼
              ┌─────────────────────────┐
              │     Eureka Server       │
              │  (Service Discovery)    │
              │       Port: 8761        │
              └─────────────────────────┘
```

---

## 📋 Tech Stack

| Technology | Version |
|------------|---------|
| **Java** | 21 |
| **Spring Boot** | 4.0.0 |
| **Spring Cloud** | 2025.1.0 |
| **PostgreSQL** | 16 |
| **MongoDB** | 7.0 |
| **Gradle** | 9.2.1 |
| **Docker** | Latest |
| **Testcontainers** | 1.20.4 |

---

## 🚀 Getting Started

### Prerequisites

- **JDK 21** or higher
- **Docker Desktop** (for databases & integration tests)
- **Gradle** (included via wrapper)

### 1. Start Databases

```bash
cd infra
docker-compose up -d
```

This starts:
- **PostgreSQL** on port `5432` (auth, doctor, appointment DBs)
- **MongoDB** on port `27017` (billing DB)

### 2. Start Eureka Server

```bash
cd opd-eureka-server
./gradlew bootRun
```

Access Eureka Dashboard: http://localhost:8761

### 3. Start Microservices

In separate terminals:

```bash
# Auth Service
cd opd-auth-service && ./gradlew bootRun

# Doctor Service
cd opd-doctor-service && ./gradlew bootRun

# Appointment Service
cd opd-appointment-service && ./gradlew bootRun

# Billing Service
cd opd-billing-service && ./gradlew bootRun

# Notification Service
cd opd-notification-service && ./gradlew bootRun

# API Gateway
cd opd-api-gateway && ./gradlew bootRun
```

### 4. Access Services

| Service | URL |
|---------|-----|
| **API Gateway** | http://localhost:8080 |
| **Eureka** | http://localhost:8761 |
| **Auth** | http://localhost:8081/health |
| **Doctor** | http://localhost:8082/health |
| **Appointment** | http://localhost:8083/health |
| **Billing** | http://localhost:8084/health |
| **Notification** | http://localhost:8085/health |

---

## 🧪 Testing

### Test Structure (Industry-Standard)

```
src/test/java/com/ztrios/<service>/
├── testconfig/              # Base classes (AbstractPostgresIT, AbstractMongoIT)
├── controller/
│   ├── *Test.java          # Unit tests
│   └── *IT.java            # Integration tests
└── config/
    └── *IT.java            # Config integration tests
```

### Run Tests

```bash
# All tests
./gradlew test

# Unit tests only
./gradlew unitTest

# Integration tests only (requires Docker)
./gradlew test --tests "*IT"

# E2E tests (when available)
./gradlew e2eTest
```

### Run Tests for Specific Service

```bash
cd opd-auth-service
./gradlew test --tests "*IT"
```

---

## 📁 Project Structure

```
opd-hospital-system/
├── infra/                      # Infrastructure (Docker Compose, scripts)
│   ├── docker-compose.yml
│   └── init-scripts/
├── opd-api-gateway/            # API Gateway
├── opd-eureka-server/          # Service Discovery
├── opd-auth-service/           # Authentication & Authorization
├── opd-doctor-service/         # Doctor Management
├── opd-appointment-service/    # Appointment Scheduling
├── opd-billing-service/        # Billing & Payments (MongoDB)
└── opd-notification-service/   # Notifications
```

---

## 🔧 Configuration

### Database Credentials

Default credentials (can be overridden via environment variables):

| Database | User | Password |
|----------|------|----------|
| PostgreSQL | `opd_user` | `opd_password` |
| MongoDB | `opd_user` | `opd_password` |

### Environment Variables

```bash
# PostgreSQL
export POSTGRES_USER=opd_user
export POSTGRES_PASSWORD=opd_password

# MongoDB
export MONGO_USER=opd_user
export MONGO_PASSWORD=opd_password
```

---

## 📊 Health Endpoints

Each service exposes health endpoints:

| Endpoint | Description |
|----------|-------------|
| `/health` | Custom health check with service info |
| `/info` | Service information (name, version, port) |
| `/actuator/health` | Spring Actuator health |
| `/actuator/info` | Spring Actuator info |

---

## 🛠️ Development

### Build All Services

```bash
# From each service directory
./gradlew build
```

### Clean Build

```bash
./gradlew clean build
```

### Skip Tests

```bash
./gradlew build -x test
```

---

## 📝 Contributing

1. Create a feature branch from `testing`
2. Write tests for new features
3. Ensure all tests pass: `./gradlew test`
4. Submit a pull request

---
