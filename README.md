# Student and Course Management System

## Introduction
The **Student and Course Management System** is a backend application developed in **Java** with **MySQL** as the database. The system provides functionality for managing students and their associated courses, including features for adding, updating, retrieving, and deleting data. The application supports role-based access for **Admin** and **Student** users, offering a robust backend solution for educational management systems.

---

## Features

### Admin Features:
- Add a new student and assign them to a course.
- Update student details, including reassigning courses.
- Retrieve all students with their associated course details.
- Retrieve students enrolled in a specific course.
- Delete a student while handling course association.

### Student Features:
- View personal details and associated course information.
- Update personal information (e.g., password).

### Optional Features:
- Transaction management for consistent updates and deletions.
- Stored procedures for streamlined database operations.

---

## Prerequisites

Ensure you have the following tools and software installed:

- **Java Development Kit (JDK)**: Version 8 or higher.
- **NetBeans IDE**: Recommended for running the project.
- **MySQL Server**: Use XAMPP for easy management.
- **MySQL Connector/J**: Required to connect Java to MySQL.
- **XAMPP**: For running MySQL and accessing phpMyAdmin.
- **Git**: For version control (optional for accessing the GitHub repository).

---

## Installation and Setup

### Step 1: Install XAMPP

1. Download and install XAMPP from [https://www.apachefriends.org/index.html](https://www.apachefriends.org/index.html).
2. Start the **MySQL** service from the XAMPP Control Panel.
3. Open **phpMyAdmin** at `http://localhost/phpmyadmin`.

### Step 2: Configure the Database

1. Create a new database named `student_course_management` in phpMyAdmin.
2. Execute the following SQL script to create the required tables:

```sql
-- Create Course Table
CREATE TABLE Course (
    course_id INT AUTO_INCREMENT PRIMARY KEY,
    course_name VARCHAR(255) NOT NULL,
    course_code VARCHAR(50) UNIQUE NOT NULL,
    course_duration INT NOT NULL
);

-- Create Student Table with Foreign Key (Course Association)
CREATE TABLE Student (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    student_name VARCHAR(255) NOT NULL,
    student_email VARCHAR(255) NOT NULL,
    student_password VARCHAR(255) NOT NULL,
    course_id INT,
    FOREIGN KEY (course_id) REFERENCES Course(course_id)
);
```

### Step 3: Set Up the Java Project

1. Download **MySQL Connector/J** from [https://dev.mysql.com/downloads/connector/j/](https://dev.mysql.com/downloads/connector/j/).
2. Add the downloaded `.jar` file to your NetBeans project:
   - Right-click the project → **Properties** → **Libraries** → **Add JAR/Folder** → Select the `.jar` file.

### Step 4: Configure Database Connection in Java

Update the database connection file (`Databaseconne.java`) with your MySQL credentials:

```java
public class Databaseconne {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/student_course_management";
        String user = "root"; // Replace with your MySQL username
        String password = ""; // Replace with your MySQL password

        return DriverManager.getConnection(url, user, password);
    }
}
```

### Step 5: Clone the Repository

1. Clone the project repository from GitHub:

```bash
git clone <repository-url>
```

2. Open the project in **NetBeans**.

### Step 6: Run the Application

1. Right-click on the `Main.java` file in NetBeans and select **Run File**.
2. Follow the on-screen prompts to perform various operations as an Admin or Student.

---

## How to Use

### Admin Operations
- **Add Student**: Enter student details and assign them to a course.
- **Retrieve All Students**: View a list of all students and their associated courses.
- **Retrieve Students by Course**: Get a list of students enrolled in a specific course.
- **Update Student Details**: Modify student information, including course reassignment.
- **Delete Student**: Remove a student record while handling course associations.

### Student Operations
- **View Details**: Display personal details and course information.
- **Update Personal Information**: Update your password or other details.

---

## Testing

1. Use the CLI application for all operations and validation.

---

## Transaction Management (Optional)

For critical operations like updates and deletions, use transaction management to ensure data consistency. Example:

```sql
START TRANSACTION;
-- Your SQL operations here
COMMIT;
-- Rollback in case of errors
ROLLBACK;
```

---

## Future Enhancements

1. Extend the backend with a RESTful API using **Spring Boot**.
2. Create a frontend using **React** or **Angular**.
3. Add more features like role-based dashboards and notifications.

---
