# 🏢 Leave Request Management System

A simple HR management system for handling employee leave requests.  
Employees can submit leave requests, and admin can review, approve, or reject them.

---

## ✅ Features

- Employee & department management
- Submit and track leave requests
- Request status: Pending / Accepted / Rejected
- Full CRUD operations
- Secured REST APIs using Spring Security and JWT
- Role-based access (USER / Admin )

---

## 🛠 Tech Stack

| Technology               | Description                     |
|--------------------------|---------------------------------|
| ☕ Java 17                | Main programming language       |
| 🌱 Spring Boot           | Backend framework               |
| 🔐 Spring Security + JWT | API protection & authentication |
| 🛢 PostgreSQL            | Relational database             |
| 📦 Spring Data JPA       | ORM layer for DB access         |
| 🧪 JUnit & Mockito       | Unit testing framework          |

---

## Project Api

================ Department ============================

- GET => localhost:8080/departments => ADMIN        [done]
- GET => localhost:8080/departments/{id} => ADMIN        [done]
- POST => localhost:8080/departments => ADMIN        [done]
- PUT => localhost:8080/departments => ADMIN        [done]
- DELETE => localhost:8080/departments => ADMIN        [done]

================ Employees =============================

- GET => localhost:8080/employees => ADMIN        [done]
- GET => localhost:8080/employees/{id} => ADMIN, USER  [done]
- POST => localhost:8080/employees => ADMIN, USER  [done]
- PUT => localhost:8080/employees => ADMIN, USER  [done]
- DELETE => localhost:8080/employees => ADMIN, USER  [done]

=====================Auth==================================

- POST => localhost:8080/auth/register => public       [done]
- POST => localhost:8080/auth/login => public       [done]

================ Users ==================================

- GET => localhost:8080/users => ADMIN        [done]
- GET => localhost:8080/users/{id} => ADMIN, USER  [done]
- GET => localhost:8080/users/{username} => ADMIN, USER  [done]
- DELETE => localhost:8080/users => ADMIN, USER  [done]

=====================Leave Request========================

- GET => localhost:8080/leave-request => ADMIN        [done]
- GET => localhost:8080/leave-request/{id} => ADMIN, USER  [done]
- POST => localhost:8080/leave-request => ADMIN, USER  [done]
- PUT => localhost:8080/leave-request => ADMIN        [done]    (change the request status if accepted or rejected)
- DELETE => localhost:8080/leave-request => ADMIN, USER  [done]
- GET => localhost:8080/leave-request/emp/{id} => ADMIN, USER  [done]

### application.properties

    spring.application.name=leave-request-management-system
    spring.messages.basename=messages
    spring.messages.encoding=UTF-8
    spring.datasource.url=jdbc:postgresql://localhost:5432/your_db
    spring.datasource.username=your user
    spring.datasource.password=your password
    spring.jpa.hibernate.ddl-auto=update
    #spring.jpa.show-sql=true
    #spring.jpa.properties.hibernate.format_sql=true
    jwt.secret.key=your secret key must be at least 32 characters

