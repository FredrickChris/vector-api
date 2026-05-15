# Vector API

Vector API is a Spring Boot backend project for managing and analyzing tasks.  
It is currently in early development, focusing on building core backend functionality, API structure, and persistence.

---

## 📌 Current Phase

**Phase 1: Core Backend Foundation (In Progress)**

The project currently includes:

### ✔ Task Management Core
- Task creation (service-level)
- Task editing and deletion
- Task model with attributes (title, subject, description, deadline, difficulty, status)

### ✔ Service Layer
- TaskService handling business logic
- AnalysisService handling sorting and filtering logic

### ✔ Repository Layer
- In-memory task storage using ArrayList
- CSV-based persistence (load/save tasks)

### ✔ REST API (Basic)
- `GET /hello` – test endpoint
- `GET /tasks` – retrieve all tasks

### ✔ Architecture
- Controller → Service → Repository structure
- Separation of concerns between logic layers

---

## 🛠 Working On (Current Focus)

**Phase 2: REST API Expansion & Cleanup**

Currently working on:

- Converting existing service methods into full REST endpoints
- Improving controller structure
- Expanding API beyond GET requests
- Improving separation between analysis logic and task service
- Cleaning and refactoring code structure for scalability

---

## 🚀 Planned Features

**Phase 3: Full REST API**

- `POST /tasks` – create tasks via API
- `PUT /tasks/{id}` – update tasks
- `DELETE /tasks/{id}` – delete tasks
- Advanced filtering endpoints (deadline, status, difficulty)
- Better error handling system

---

**Phase 4: Data & Persistence Upgrade**

- Replace CSV storage with database (MySQL/PostgreSQL)
- Introduce JPA / Hibernate
- Improve data consistency and querying

---

**Phase 5: Advanced Backend Features**

- Authentication system (Spring Security)
- User-based task separation
- Role-based access (future expansion)
- API validation improvements

---

## 🧠 Project Goal

To build a fully functional backend system that demonstrates:
- Clean architecture design
- REST API development
- Data persistence strategies
- Scalable backend structure using Spring Boot

---

## 📁 Project Structure

com.engalladofc.vector
├── VectorApplication.java
├── controller
│ └── TaskController.java
├── service
│ ├── TaskService.java
│ └── AnalysisService.java
├── repository
│ └── TaskRepository.java
├── model
│ ├── Task.java
│ ├── Status.java
│ ├── Error.java
│ └── ViewResult.java

---

## 👤 Author

Fredrick Chris Engallado  
Backend learning project focused on Java + Spring Boot development
