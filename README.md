# User Management Backend with Ktor and Clean Architecture

This project is a simple backend application developed using **Ktor** and **Exposed** ORM in **Kotlin**. It follows **Clean Architecture** principles, making it modular, scalable, and testable. The backend supports basic user management operations like adding and retrieving users, with an in-memory H2 database setup.

## Project Structure

The project is organized based on Clean Architecture principles:

- **Domain Layer**: Core business logic and model definitions.
- **Application Layer**: Contains use cases for business operations.
- **Interface Adapters Layer**: Implements interfaces to interact with external systems and defines routing for API endpoints.
- **Frameworks & Drivers Layer**: Infrastructure and configuration code, such as database and server setup.

## Prerequisites

- **Kotlin** (latest version recommended)
- **Ktor** (for server setup)
- **Exposed** (for database operations)
- **H2 Database** (in-memory for development)

## Instructions

To test the add user functionality, follow these steps:

1. **Use a REST Client (cURL Postman)**

  - **Endpoint**: `POST http://localhost:8080/addUser` or access the form at `http://localhost:8080/static/add_user.html`
  - **Description**: Adds a new user to the database.
  - **Request Format**: JSON body with `name` and `email` fields.

   **Example JSON Body**:
   ```json
   {
       "name": "Jane Doe",
       "email": "jane.doe@example.com"
   }
   ```  
  ```bash
  curl -X POST http://localhost:8080/addUser \
     -H "Content-Type: application/json" \
     -H "Accept: application/json" \
     -d '{"name": "Jane Doe", "email": "jane.doe@example.com"}'
  ```

2. **Retrieve All Users (Optional)**

  - **Endpoint**: `GET http://localhost:8080/getUsers`
  - **Description**: Retrieves a list of all users in the database.

   **Example cURL Command**:
   ```bash
   curl -X GET http://localhost:8080/getUsers \
     -H "Accept: application/json" 
```