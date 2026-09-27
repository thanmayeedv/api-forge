# API Forge

An enterprise-grade API management platform designed to provision project environments, issue dynamic API keys, track real-time usage quotas, and monitor system health.

## Tech Stack

* **Backend:** Java 21, Spring Boot (Security, JPA, Actuator)
* **Database & Persistence:** PostgreSQL 17, Hibernate ORM
* **Security & Authentication:** BCrypt hashing, Stateless Token Architecture
* **Frontend Operations Dashboard:** Tailwind CSS (via CDN), Asynchronous JavaScript Fetch API
* **DevOps & Tooling:** Maven, Git, Docker-ready structure

## Architecture & Features

1. **User Identity & Security Management:**
   * Secure user registration with BCrypt password encoding.
   * Relational mapping with PostgreSQL to persist user profiles and audit trails.
   * Client-side search and filtering for active database registries.

2. **API Key & Quota Engine:**
   * Dynamic generation of environment-specific API keys (`DEV` / `PROD`).
   * Configurable request limit quotas assigned per project token.
   * Middleware-ready architecture for real-time validation and rate-limiting enforcement.

3. **System Health & Observability:**
   * Integrated Spring Boot Actuator endpoint (`/actuator/health`) for real-time service monitoring.
   * Live operations badge reflecting system availability and uptime.

## Getting Started Locally

### Prerequisites
* Java 21 SDK installed
* PostgreSQL 17 running locally on port `5432`
* Maven installed

### 1. Configure Database
Create a PostgreSQL database named according to your application configuration (e.g., `api_for_gin_pgadmin4`), and update your credentials in `src/main/resources/application.yaml`.

### 2. Run the Backend
Clone the repository and run the Spring Boot application using Maven:
```bash
git clone [https://github.com/thanmayeedv/api-forge.git](https://github.com/thanmayeedv/api-forge.git)
cd api-forge
mvn spring-boot:run
