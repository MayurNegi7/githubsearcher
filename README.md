## 📘 GitHub Repository Searcher

This is a **Spring Boot** backend service that allows users to **search GitHub repositories** by query, language, and sort criteria using the GitHub API.  
The data is stored efficiently in a database using upsert logic and can be filtered by various parameters.

---

### 🚀 Features

- ✅ RESTful API (No UI)
- 🔍 Search GitHub repositories using keywords, language, and sorting
- 📅 Saves results to database (with upsert)
- 🔄 Filter repositories by language, stars, forks, or last updated
- ✅ TDD with **JUnit**
- ✅ GitHub token protected via `application.properties`
- 🧪 Postman-ready

---

### 📦 Tech Stack

- **Java 17+**
- **Spring Boot**
- **PostgreSQL (preferred)**
- **Lombok**
- **JUnit 5**
- **Mockito**
- **Maven**

---

### 🔧 Setup Instructions

#### 👜 1. Clone the repo

```bash
git clone https://github.com/MayurNegi7/githubsearcher.git
cd 
```

#### ⚙️ 2. Configure GitHub Token

- Update application.properties:

```bash
github.token=your_github_pat_here

```
####🛢️ 3. Configure PostgreSQL Database

- Update your application.properties:
```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/githubsearcher
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
```
- Make sure PostgreSQL is running and create the database manually if needed:
```bash
CREATE DATABASE githubsearcher;
```
####▶️ Run the Project
```bash
mvn spring-boot:run
```
🧪 Testing

```bash
mvn test
```
#### 📬 API Endpoints
```bash
POST /api/github/search
```
- Request Body (JSON):
```bash
{
  "query": "spring boot",
  "language": "Java",
  "sort": "stars"
}
```
-🧹 Filter Repositories
```bash
GET /api/github/filter?language=Java&minStars=100&sort=stars
```
---
#### 📂 Project Structure
```bash
src/
├── controller         # REST Controllers
├── dto                # Request DTOs
├── model              # Entity Models
├── repository         # JPA Repositories
├── service            # Business Logic
└── resources/
    └── application.properties
```
