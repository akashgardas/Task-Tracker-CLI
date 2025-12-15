# Task Tracker CLI 📝

A **Spring Boot–based Command Line Interface (CLI) application** to manage and track daily tasks.
This project demonstrates how Spring Boot can be used beyond web applications to build **clean, layered, and persistent CLI tools**.

---

## 🚀 Features

* Add, update, and delete tasks
* Mark tasks as **TODO**, **IN_PROGRESS**, or **DONE**
* List all tasks or filter by status
* Persist tasks in a local **JSON file**
* Data survives application restarts
* Clean layered architecture (CLI → Service → Repository)

---

## 🛠 Tech Stack

* **Java 22**
* **Spring Boot 4**
* **Maven**
* **Jackson (JSON serialization/deserialization)**
* File-based persistence (`tasks.json`)

---

## 🧱 Project Architecture

```
CLI (CommandLineRunner)
        ↓
Service Layer (Business Logic)
        ↓
Repository Layer (JSON File Persistence)
```

**Key Design Principles**

* Separation of concerns
* Dependency Injection
* Interface-based repository design
* Restart-safe persistence

---

## 📂 Project Structure

```
src/main/java/com/example/tasktrackercli
│
├── cli
│   └── TaskCommandRunner.java
│
├── service
│   └── TaskService.java
│
├── repository
│   ├── TaskRepository.java
│   └── JsonFileTaskRepository.java
│
├── model
│   ├── Task.java
│   └── TaskStatus.java
│
├── config
│   └── JacksonConfig.java
│
└── TaskTrackerCliApplication.java
```

---

## ▶️ How to Run

### Prerequisites

* Java 22+
* Maven

### Run the application

```bash
mvn spring-boot:run
```

### Using IntelliJ (Recommended)

1. Open **Run → Edit Configurations**
2. Enable **Program arguments**
3. Enter commands (examples below)
4. Click **Run**

---

## 📌 CLI Commands

### Add a task

```
add "Buy groceries"
```

### Update a task

```
update 1 "Buy groceries and cook dinner"
```

### Delete a task

```
delete 1
```

### Mark task status

```
mark-in-progress 1
mark-done 1
```

### List tasks

```
list
list todo
list in-progress
list done
```

---

## 💾 Persistence

* Tasks are stored in a local file: **`tasks.json`**
* The file is automatically created if it does not exist
* Handles empty or corrupted files safely
* Uses Jackson with Java Time support for date handling

---

## 🎯 Learning Outcomes

This project helped me understand:

* Spring Boot application lifecycle
* Building CLI apps with `CommandLineRunner`
* Dependency Injection and layered architecture
* File-based persistence with JSON
* Jackson serialization & deserialization
* Handling real-world edge cases (empty files, restarts)

---

## 🔮 Future Improvements

* Add unit tests (JUnit + Mockito)
* Improve CLI UX and validations
* Convert CLI into a RESTful API
* Replace JSON with a database (MySQL/PostgreSQL)
* Package as an executable JAR

---

## 👤 Author

**Akash Gardas**
B.Tech Student | Java & Spring Boot Learner

---

**Project URL**: [https://roadmap.sh/projects/task-tracker](https://roadmap.sh/projects/task-tracker)

---

⭐ If you find this project useful, feel free to star the repository!
