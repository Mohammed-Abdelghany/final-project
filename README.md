# E-commerce Full-Stack Application

## 🛒 Overview

This is a full-stack e-commerce application built with **Spring Boot** (backend), **Angular** (frontend), and **Oracle Database** for persistence. The application allows users to register, login, browse products, manage categories, and submit contact messages. Admins can manage products, categories, and users. It follows modern best practices, including JWT-based authentication, RESTful APIs, and exception handling.

## ⚡ Features

* User Registration and Authentication (JWT)
* Role-based Authorization (User/Admin)
* CRUD Operations for Products and Categories
* Contact Message Submission
* Pagination and Filtering for Product Listings
* Global Exception Handling
* Swagger/OpenAPI Documentation
* Angular-based responsive frontend

## 🏗️ Technologies Used

**Backend:**

* Java 17+
* Spring Boot 3.x
* Spring Security
* Spring Data JPA
* Oracle Database
* JWT Authentication
* Springdoc OpenAPI / Swagger
* Maven

**Frontend:**

* Angular 16+
* TypeScript
* Angular Material / Bootstrap 
* HttpClient for REST API calls

**Other Tools:**
* Git & GitHub
* Postman (API testing)
* Maven for build management

## 📦 Project Structure

```
ecommerce/
├── config/
│   ├── exception/          # Global exception handler
│   ├── filters/            # JWT, Auth filters
│   ├── SecurityConfig.java
│   └── WebConfig.java
├── controller/             # REST Controllers
│   ├── AuthController.java
│   ├── ProductController.java
│   ├── CategoryController.java
│   ├── UserController.java
│   └── ContactMessageController.java
├── dto/                    # Data Transfer Objects
├── helper/                 # Utility classes (JWT token, pagination, file storage)
├── service/                # Service layer
└── repository/             # JPA Repositories
```

## ⚙️ Backend Setup

1. **Clone the repository**

```bash
git clone <https://github.com/Mohammed-Abdelghany/final-project/>
cd backend
```

2. **Configure Oracle DB**

   * Update `application.properties` / `application.yml`:

```properties
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:XE
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

3. **Build and Run Backend**

```bash
mvn clean install
mvn spring-boot:run
```

4. **Swagger API Docs**

* Access: `http://localhost:9090/swagger-ui/index.html`

## ⚙️ Frontend Setup

1. **Navigate to frontend directory**

```bash
cd frontend
```

2. **Install dependencies**

```bash
npm install
```

3. **Run Angular app**

```bash
ng serve --open
```

* The app will run on `http://localhost:4200`

## 🔐 Authentication

* JWT-based authentication
* Roles: `USER` and `Chef.`
* Protected endpoints configured via Spring Security, JWT



## 📂 Future Improvements

* Implement payment gateway integration
* Add product reviews and ratings
* Add advanced search and filter options
* Improve frontend UI/UX


