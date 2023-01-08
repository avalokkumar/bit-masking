# bit-masking
This project demonstrates how to use bit masking to store enums in database tables

# Spring Service for Managing Users and Permissions

This service provides APIs for creating, updating, and deleting users and assigning permissions to them. It is built using the Spring framework and uses a MySQL database to store user and permission data.

## Prerequisites

- Java 8+
- MySQL 5.6+

## Getting Started

1. Clone the repository: `git clone https://github.com/example/spring-users-permissions.git`
2. Create a MySQL database and run the `schema.sql` script to create the necessary tables.
3. Modify the `application.properties` file to specify your MySQL connection details.
4. Build the project using Maven: `mvn clean install`
5. Run the service using the `spring-boot:run` goal.

## API Documentation

The API documentation can be found in the `docs` folder or at the following URL: `http://localhost:8080/docs` when the service is running.

## Contributing

To contribute to this project, please follow the contribution guidelines in the `CONTRIBUTING.md` file.