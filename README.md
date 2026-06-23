# NexusHR Backend

NexusHR is a modern, microservices-based Human Resources Management System designed for scalability and performance.

## Architecture & Tech Stack
- **Java 21** & **Spring Boot 3**: Core framework for robust backend development.
- **Microservices Architecture:** 
  - `auth-service`: Dedicated service for authentication, JWT generation, login, and user registration.
  - `employee-service`: Core service managing employee profiles, attendance tracking, and leave requests.
  - `api-gateway`: Single entry point for routing client requests (foundation laid).
  - `payroll-service`: Future module for salary computation.
  - `common`: Shared entity models and utilities across all microservices.
- **Database:** PostgreSQL with **Flyway** for automated, version-controlled schema migrations.
- **Caching:** Redis integration for high-performance data retrieval and caching.
- **Security:** Spring Security with stateless JWT (JSON Web Token) authentication and Argon2 password hashing.

## Implemented Features
- **Stateless Authentication:** Secure login and registration flows generating JWTs for sessionless authorization.
- **Role-Based Access Control (RBAC):** Granular endpoint protection using `@PreAuthorize` ensuring only authorized personnel (e.g., `ROLE_HR`, `ROLE_ADMIN`) can perform sensitive actions.
- **Employee Management:** Full CRUD operations for creating, reading, and deleting employee profiles.
- **Attendance Tracking:** Endpoints for employees to clock in and clock out, creating immutable daily records.
- **Leave Management:** System allowing employees to submit leave requests (Vacation, Sick, etc.) with an integrated approval workflow restricted exclusively to HR and Administrators.
