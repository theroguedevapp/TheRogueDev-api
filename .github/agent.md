# Project Overview

## Context
TheRogueDev-api is a Spring Boot application designed to provide a robust API for managing various resources. It leverages technologies such as Spring Security, Spring Data JPA, and OpenFeign for microservice communication. The project is structured to support modular development and scalability.

## Workspace Structure
- **src/main/java**: Contains the main application code, organized by feature modules.
  - `config`: Configuration classes for security, Swagger, and other application-wide settings.
  - `exceptions`: Custom exception classes for handling specific error scenarios.
  - `user`, `file`, `publication`, etc.: Feature-specific modules with controllers, services, repositories, and DTOs.
- **src/main/resources**: Contains application properties, database migration scripts, and static resources.
- **build.gradle**: Gradle build configuration file.
- **docker-compose.yml**: Docker Compose configuration for containerized deployment.

## Best Practices
1. **Coding Standards**:
   - Follow Java conventions for naming, formatting, and documentation.
   - Use meaningful commit messages and adhere to the repository's branching strategy.

2. **Testing**:
   - Write unit and integration tests for all new features.
   - Ensure tests cover edge cases and error scenarios.

3. **Documentation**:
   - Update `README.md` and other relevant documentation for any significant changes.
   - Avoid generating `.sh`, `.ps1`, and `.md` files unless explicitly requested.

4. **Security**:
   - Validate all user inputs to prevent injection attacks.
   - Use environment variables for sensitive configurations.

5. **Performance**:
   - Optimize database queries and avoid N+1 query problems.
   - Use caching where appropriate to improve response times.
