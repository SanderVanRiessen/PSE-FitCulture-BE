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

# API Documentation

## Article endpoints

### 1. Get Single Article

**Endpoint:** `GET /public/article/{id}`

**Description:** Retrieves a single article by its ID.

**Parameters:**
- `id` (Path Variable, Long): The unique identifier of the article to retrieve.

**Responses:**
- `200 OK`: The article was found and is returned in the response body.
- `404 Not Found`: The article with the specified ID does not exist.

### Add Article

**Endpoint:** `POST /articles`

**Description:** Adds a new article.

**Request Body:**
- `title` (String, required): The title of the article.
- `body` (String, required): The content/body of the article.
- `author` (String, required): The author of the article.
- `date` (String, required): The date of the article in ISO 8601 format (e.g., "2024-06-06T12:00:00.000Z").

**Responses:**
- `201 Created`: The article was successfully added. The newly created article is returned in the response body.
- `403 Forbidden`: The user is not authorized to add articles.

### Get Articles Headlines

**Endpoint:** `GET /public/articlesHeadlines`

**Description:** Retrieves headlines of all articles.

**Responses:**
- `200 OK`: The headlines of all articles are returned in the response body.

## Forum Controller Endpoints

### Get All Categories

**Endpoint:** `GET /public/forum/all`

**Description:** Retrieves all categories.

**Responses:**
- `200 OK`: Returns a list of all categories.
- `204 No Content`: No categories found.

### Get Categories with Recent Topics

**Endpoint:** `GET /public/forum/frontpage`

**Description:** Retrieves categories with their recent topics.

**Responses:**
- `200 OK`: Returns a list of categories with recent topics.
- `204 No Content`: No categories with recent topics found.

### Get All Topics by Category ID

**Endpoint:** `GET /public/forum/{categoryId}/topics`

**Description:** Retrieves all topics within a category by category ID.

**Parameters:**
- `categoryId` (Path Variable, Long): The ID of the category.

**Responses:**
- `200 OK`: Returns a list of topics within the specified category.
- `204 No Content`: No topics found.

### Get Topic by ID

**Endpoint:** `GET /public/forum/{topicId}`

**Description:** Retrieves a topic by its ID.

**Parameters:**
- `topicId` (Path Variable, Long): The ID of the topic.

**Responses:**
- `200 OK`: Returns the details of the specified topic.
- `404 Not Found`: No topic found with the specified ID.

### Get Posts by Topic ID

**Endpoint:** `GET /public/forum/{topicId}/posts`

**Description:** Retrieves all posts within a topic by topic ID.

**Parameters:**
- `topicId` (Path Variable, Long): The ID of the topic.

**Responses:**
- `200 OK`: Returns a list of posts within the specified topic.
- `204 No Content`: No posts found for the specified topic.

### Create Topic

**Endpoint:** `POST /forum/topic`

**Description:** Creates a new topic.

**Request Body:**
- `title` (String, required): The title of the topic.
- `content` (String, required): The content of the topic.

**Responses:**
- `200 OK`: Returns the details of the newly created topic.

### Create Post

**Endpoint:** `POST /forum/{topicId}/post`

**Description:** Creates a new post within a topic.

**Parameters:**
- `topicId` (Path Variable, Long): The ID of the topic.

**Request Body:**
- `content` (String, required): The content of the post.

**Responses:**
- `200 OK`: Returns the details of the newly created post.

### Delete Topic

**Endpoint:** `DELETE /forum/topic/{topicId}`

**Description:** Deletes a topic.

**Parameters:**
- `topicId` (Path Variable, Long): The ID of the topic.

**Responses:**
- `200 OK`: Topic successfully deleted.
- `404 Not Found`: No topic found with the specified ID.

### Delete Post

**Endpoint:** `DELETE /forum/post/{postId}`

**Description:** Deletes a post.

**Parameters:**
- `postId` (Path Variable, Long): The ID of the post.

**Responses:**
- `200 OK`: Post successfully deleted.
- `404 Not Found`: No post found with the specified ID.
