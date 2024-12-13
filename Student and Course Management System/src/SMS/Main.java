package SMS;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                System.out.println("---------------Welcome to Student and Course Management System---------------------");
                System.out.println("Who you are?");
                System.out.println("1. Admin");
                System.out.println("2. Student");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                if (choice == 1) {
                    // Admin login
                    System.out.print("Admin -\nEnter your username: ");
                    String adminUsername = scanner.nextLine();
                    System.out.print("Enter your password: ");
                    String adminPassword = scanner.nextLine();

                    if (StudentCourseManager.loginAdmin(adminUsername, adminPassword)) {
                        System.out.println("Login Successful");
                        adminOperations(scanner);
                    } else {
                        System.out.println("Invalid credentials. Please try again.");
                    }
                } else if (choice == 2) {
                    
                    System.out.print("Student -\nEnter your username: ");
                    String studentUsername = scanner.nextLine();
                    System.out.print("Enter your password: ");
                    String studentPassword = scanner.nextLine();

                    if (StudentCourseManager.loginStudent(studentUsername, studentPassword)) {
                        System.out.println("Login Successful");
                        studentOperations(scanner);
                    } else {
                        System.out.println("Invalid credentials. Please try again.");
                    }
                } else if (choice == 3) {
                    System.out.println("Thank you for using the Student and Course Management System.");
                    break;
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private static void adminOperations(Scanner scanner) throws SQLException {
        while (true) {
            System.out.println("Choose an operation:");
            System.out.println("1. Add a new student");
            System.out.println("2. Retrieve all student details");
            System.out.println("3. Update a student's details");
            System.out.println("4. Delete a student");
            System.out.println("5. Retrieve student enrolled in specific course");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter student id: ");
                    int studentId = scanner.nextInt();  // Reads the student ID

                    scanner.nextLine(); // Clear the newline character in the buffer

                    System.out.print("Enter student name: ");
                    String studentName = scanner.nextLine();  

                    System.out.print("Enter student email: ");
                    String studentEmail = scanner.nextLine();  

                    System.out.print("Enter course ID: ");
                    int courseId = scanner.nextInt(); 

                    scanner.nextLine(); 
                    
                    System.out.print("Enter student password: ");
                    int pass = scanner.nextInt(); 

                    scanner.nextLine(); 

                    System.out.print("Enter course name: ");
                    String courseName = scanner.nextLine(); 

                    System.out.print("Enter course code: ");
                    int courseCode = scanner.nextInt();  

                    System.out.print("Enter course duration: ");
                    int courseDuration = scanner.nextInt(); 

                    StudentCourseManager.addStudent(studentId, studentName, studentEmail, courseId, pass, courseName, courseCode, courseDuration);
                    System.out.println("Student added successfully!");
                    break;

                case 2:
                    System.out.println("Fetching student details...");
                    StudentCourseManager.getStudentDetails();
                    break;

                case 3:
                    System.out.print("Enter student ID to update: ");
                    int studentIdToUpdate = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    System.out.print("Enter new student name: ");
                    String updatedName = scanner.nextLine();

                    System.out.print("Enter new student email: ");
                    String updatedEmail = scanner.nextLine();

                    System.out.print("Enter new course ID: ");
                    int updatedCourseId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    System.out.print("Enter new course name: ");
                    String updatedCourseName = scanner.nextLine();

                    System.out.print("Enter new course code: ");
                    int updatedCourseCode = scanner.nextInt();

                    System.out.print("Enter new course duration: ");
                    int updatedCourseDuration = scanner.nextInt();

                    try {
                        StudentCourseManager.updateStudent(
                            studentIdToUpdate,
                            updatedName,
                            updatedEmail,
                            updatedCourseId,
                            updatedCourseName,
                            updatedCourseCode,
                            updatedCourseDuration
                        );
                        System.out.println("Student details updated successfully!");
                    } catch (SQLException e) {
                        System.out.println("Error updating student details: " + e.getMessage());
                    }
                    break;

                case 4:
                    System.out.print("Enter student ID to delete: ");
                    int studentIdToDelete = scanner.nextInt();

                    try {
                        StudentCourseManager.deleteStudent(studentIdToDelete);
                        System.out.println("Student deleted successfully!");
                    } catch (SQLException e) {
                        System.out.println("Error deleting student: " + e.getMessage());
                    }
                    break;

                case 5:
                    System.out.print("Enter course name to fetch students: ");

                    String courseNamek = scanner.next();

                    try {
                        StudentCourseManager.getStudentsByCourse(courseNamek);
                    } catch (SQLException e) {
                        System.out.println("Error retrieving students: " + e.getMessage());
                    }
                    break;

                case 6:
                    System.out.println("Returning to the main menu...");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
                
            }
        }
    }

    private static void studentOperations(Scanner scanner) throws SQLException {
        while (true) {
            System.out.println("Choose an operation:");
            System.out.println("1. View your details");
            System.out.println("2. Change your password");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // View student details
                    System.out.print("Enter your student ID: ");
                    int studentId = scanner.nextInt();
                    StudentCourseManager.viewStudentDetails(studentId);
                    break;
                case 2:
                    // Change student password
                    System.out.print("Enter your student ID: ");
                    studentId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter new password: ");
                    String newPassword = scanner.nextLine();
                    StudentCourseManager.changeStudentPassword(studentId, newPassword);
                    break;
                case 3:
                    // Exit
                    System.out.println("Thank you for using the Student and Course Management System.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
