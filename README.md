# README for Spring Boot Application: FitCulture

## Overview

FitCulture is a Spring Boot-based application designed to manage fitness culture within organizations. It offers functionality to manage user profiles, including details such as name, email, roles, and more, in a secure and scalable manner.

## Features

- User management with extended attributes (name, email, password, role).
- Secure password handling with hashed storage.
- Role-based access control.
- Scalable repository pattern for database access.

## Technology Stack

- **Spring Boot**: Framework for creating stand-alone, production-grade Spring-based applications.
- **MySQL**: Relational database management system for storing application data.
- **Hibernate/JPA**: Object-relational mapping tool for database access.
- **Flyway**: Database migration tool for version control and migration of database schemas.

## Prerequisites

- Java JDK 11 or later.
- Maven 3.2+ (for project building).
- MySQL Server (for database).
- An IDE such as IntelliJ IDEA or Eclipse (optional, but recommended).

## Setup and Installation

1. **Clone the Repository**: Clone the FitCulture repository to your local machine using `git clone`.

2. **Database Setup**:
    - Ensure MySQL Server is installed and running on your local machine.
    - Create a new database named `fitculture-db`.
    - Configure your database connection settings in `src/main/resources/application.properties`:

      ```
      spring.datasource.url=jdbc:mysql://localhost:3306/fitculture-db
      spring.datasource.username=root
      spring.datasource.password=<YourDatabasePassword>
      ```

3. **Run Migrations**:
    - Migrations are managed via Flyway and are located in `src/main/resources/migrations`.
    - To create a new migration, add a SQL file in the migrations directory following the naming convention `V<Version>__<Description>.sql`. For example, `V2__Add_roles_table.sql`.
    - Flyway will automatically apply migrations on application startup.

4. **Build and Run**:
    - Navigate to the root directory of the project.
    - Build the project using Maven: `mvn clean install`.
    - Run the application: `mvn spring-boot:run`.
    - The application will be accessible at `http://localhost:8087/`.

## Repository Setup and Usage

FitCulture utilizes a generic repository pattern to abstract and encapsulate the data access layer. This setup allows for easy extension and maintenance of the application's persistence layer.

### Existing Repository

- The `UserRepositoryJpa` class extends the generic `AbstractEntityRepositoryJpa` class, providing a concrete implementation for user entity management.

### Creating a New Repository

To create a new repository for a different entity:

1. **Define Your Entity**: Ensure your new entity is annotated with `@Entity` and implements the `Identifiable` interface.

2**Implement the Repository**: Create a new class that extends `AbstractEntityRepositoryJpa<T>`, providing the entity class as the generic parameter.

```java
 @Repository
    public class MyEntityRepository extends AbstractEntityRepositoryJpa<MyEntity> {
  }
   ```

4. **Use the Repository**: Autowire the repository in your service class to perform CRUD operations.
