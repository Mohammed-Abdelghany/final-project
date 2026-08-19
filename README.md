# 🛒 Enterprise Full-Stack E-Commerce Platform

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Angular](https://img.shields.io/badge/Angular-17%2B-DD0031.svg)](https://angular.io/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue.svg)](https://www.postgresql.org/)
[![Kashier Payment](https://img.shields.io/badge/Payment-Kashier%20Gateway-orange.svg)](https://kashier.io/)

A high-performance E-Commerce platform built with **Spring Boot 3**, **Angular 17+**, and **PostgreSQL**. Integrated with **Kashier Payment Gateway** for secure checkout using **Clean Architecture** and **DDD** principles.

---
## ⚡ Core Features

### 🔐 Security & Access Control
* **Stateless JWT:** Secure authentication flow with refresh tokens.
* **Granular RBAC:** Role-based access for `ADMIN`, `SELLER`, and `CUSTOMER`.
* **Protection:** BCrypt hashing and API rate-limiting against brute force.

### 📦 Catalog & Performance
* **Catalog & Search:** Dynamic product management with paginated dynamic filters.
* **Redis Caching:** L2 caching for high-traffic catalog endpoints.
* **Database Optimization:** PostgreSQL schema with custom indexing and Liquibase versioning.

---

## 🏗️ Tech Stack

| Layer | Technologies |
| :--- | :--- |
| **Backend** | Java 17+, Spring Boot 3.x, Spring Data JPA, Spring Security |
| **Payment** | Kashier Payment API & Webhook Integration |
| **Database & Cache** | PostgreSQL 16, Redis, Liquibase |
| **Frontend** | Angular 17+, TypeScript, RxJS, Angular Material |
| **DevOps & Docs** | Docker, Docker Compose, Swagger UI (OpenAPI 3.0) |

---

## 📂 Architecture

```
ecommerce-platform/
├── src/main/java/com/ecommerce/
│   ├── config/          # Security, Redis & Kashier Configs
│   ├── controller/      # REST & Webhook API Layer
│   ├── dto/             # Data Transfer & Payment Payloads
│   ├── model/           # JPA Entities
│   ├── repository/      # Spring Data JPA Repositories
│   └── service/         # Business Logic & Kashier Payment Engine
└── src/main/resources/  # Migration Scripts & Configs
```

---

## ⚙️ Quick Start (Docker)

```bash
git clone <your-repository-url>
cd ecommerce-platform
docker-compose up -d --build
```

Endpoints:
* **Frontend:** `http://localhost:4200`
* **Swagger Docs:** `http://localhost:8080/swagger-ui/index.html`

---

## 🛠️ Configuration (`application.yml`)

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/ecommerce_db
    username: postgres
    password: your_password

kashier:
  merchant-id: YOUR_MERCHANT_ID
  api-key: YOUR_KASHIER_API_KEY
  secret-key: YOUR_KASHIER_SECRET_KEY
  mode: test
```

---

## 🔐 Key API Endpoints

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/v1/auth/login` | Authenticate user & issue JWT |
| `GET` | `/api/v1/products` | Paginated product search with filters |
| `POST` | `/api/v1/payments/create-checkout-session` | Initialize Kashier payment |
| `POST` | `/api/v1/payments/kashier-webhook` | Kashier payment status webhook |
