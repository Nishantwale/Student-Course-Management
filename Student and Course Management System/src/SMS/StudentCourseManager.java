package SMS;
import java.sql.*;

public class StudentCourseManager {
    
    
    public static boolean loginAdmin(String username, String password) throws SQLException {
        String query = "SELECT * FROM admin WHERE username = ? AND password = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_course_management", "root", "");
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // If the result set has any rows, the login is successful
        }
    }

    public static boolean loginStudent(String username, String password) throws SQLException {
        String query = "SELECT * FROM students WHERE student_email = ? AND password = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_course_management", "root", "");
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); 
        }
    }

    // Add a new student with a course assignment
    public static void addStudent(int studentId, String studentName, String studentEmail, int courseId,int pass, String courseName, int courseCode, int courseDuration) throws SQLException {
    String checkCourseQuery = "SELECT COUNT(*) FROM courses WHERE course_id = ?";
    String addCourseQuery = "INSERT INTO courses (course_id, course_name, course_code,course_duration) VALUES (?, ?, ?,?)";
    String insertStudentQuery = "INSERT INTO students (student_id,student_name, student_email, course_id, password) VALUES (?, ?, ?,?,?)";

    try (Connection connection = Databaseconne.getConnection();
         PreparedStatement checkStmt = connection.prepareStatement(checkCourseQuery);
         PreparedStatement addCourseStmt = connection.prepareStatement(addCourseQuery);
         PreparedStatement insertStudentStmt = connection.prepareStatement(insertStudentQuery)) {

        // Check if the course exists
        checkStmt.setInt(1, courseId);
        ResultSet rs = checkStmt.executeQuery();

        if (rs.next() && rs.getInt(1) == 0) {
            // If course doesn't exist, add it with placeholder data
            addCourseStmt.setInt(1, courseId);
            addCourseStmt.setString(2, courseName); // Replace with dynamic input if needed
            addCourseStmt.setInt(3, courseCode);
            addCourseStmt.setInt(4, courseDuration);// Replace with dynamic input if needed
            addCourseStmt.executeUpdate();
        }else{
            System.out.println("Course ID " + courseId + " is already exist.");
        }

        // Insert the student
        insertStudentStmt.setInt(1, studentId);
        insertStudentStmt.setString(2, studentName);
        insertStudentStmt.setString(3, studentEmail);
        insertStudentStmt.setInt(4, courseId);
        insertStudentStmt.setInt(5, pass);
        insertStudentStmt.executeUpdate();

        System.out.println("Student added successfully!");
    }
}


    // Retrieve student details along with course information
    public static void getStudentDetails() throws SQLException {
    String query = "SELECT students.student_id, students.student_name, students.student_email, courses.course_id, courses.course_name, courses.course_code, courses.course_duration " +
            "FROM students " +
            "JOIN courses ON students.course_id = courses.course_id";

    try (Connection connection = Databaseconne.getConnection();
         Statement stmt = connection.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {

        
        System.out.printf("%-15s %-25s %-30s %-10s %-25s %-15s %-15s%n", 
                          "Student ID", "Student Name", "Student Email", 
                          "Course ID", "Course Name", "Course Code", "Course Duration");
        System.out.println("-----------------------------------------------------------------------------------------------");

        
        while (rs.next()) {
            System.out.printf("%-15d %-25s %-30s %-10d %-25s %-15d %-15d%n", 
                              rs.getInt("student_id"), rs.getString("student_name"), 
                              rs.getString("student_email"), rs.getInt("course_id"), 
                              rs.getString("course_name"), rs.getInt("course_code"), 
                              rs.getInt("course_duration"));
        }
    }
}

    // Update student details, including course modification
    public static void updateStudent(int studentId, String studentName, String studentEmail, int courseId, String courseName, int courseCode, int courseDuration) throws SQLException {
    String checkCourseQuery = "SELECT COUNT(*) FROM courses WHERE course_id = ?";
    String addCourseQuery = "INSERT INTO courses (course_id, course_name, course_code, course_duration) VALUES (?, ?, ?, ?)";
    String updateCourseQuery = "UPDATE courses SET course_name = ?, course_code = ?, course_duration = ? WHERE course_id = ?";
    String updateStudentQuery = "UPDATE students SET student_name = ?, student_email = ?, course_id = ? WHERE student_id = ?";

    try (Connection connection = Databaseconne.getConnection();
         PreparedStatement checkCourseStmt = connection.prepareStatement(checkCourseQuery);
         PreparedStatement addCourseStmt = connection.prepareStatement(addCourseQuery);
         PreparedStatement updateCourseStmt = connection.prepareStatement(updateCourseQuery);
         PreparedStatement updateStudentStmt = connection.prepareStatement(updateStudentQuery)) {

        // Check if the course exists
        checkCourseStmt.setInt(1, courseId);
        ResultSet rs = checkCourseStmt.executeQuery();

        if (rs.next() && rs.getInt(1) == 0) {
            // If course doesn't exist, add it
            addCourseStmt.setInt(1, courseId);
            addCourseStmt.setString(2, courseName);
            addCourseStmt.setInt(3, courseCode);
            addCourseStmt.setInt(4, courseDuration);
            addCourseStmt.executeUpdate();
            System.out.println("Course ID " + courseId + " did not exist and has been added.");
        } else {
            // If course exists, update its details
            updateCourseStmt.setString(1, courseName);
            updateCourseStmt.setInt(2, courseCode);
            updateCourseStmt.setInt(3, courseDuration);
            updateCourseStmt.setInt(4, courseId);
            updateCourseStmt.executeUpdate();
            System.out.println("Course ID " + courseId + " already exists and has been updated.");
        }

        // Update the student details
        updateStudentStmt.setString(1, studentName);
        updateStudentStmt.setString(2, studentEmail);
        updateStudentStmt.setInt(3, courseId);
        updateStudentStmt.setInt(4, studentId);
        updateStudentStmt.executeUpdate();

        System.out.println("Student details updated successfully!");
    }
}


    // Delete a student
    public static void deleteStudent(int studentId) throws SQLException {
    String getCourseIdQuery = "SELECT course_id FROM students WHERE student_id = ?";
    String deleteStudentQuery = "DELETE FROM students WHERE student_id = ?";
    String checkOtherStudentsQuery = "SELECT COUNT(*) FROM students WHERE course_id = ?";
    String deleteCourseQuery = "DELETE FROM courses WHERE course_id = ?";

    try (Connection connection = Databaseconne.getConnection();
         PreparedStatement getCourseStmt = connection.prepareStatement(getCourseIdQuery);
         PreparedStatement deleteStudentStmt = connection.prepareStatement(deleteStudentQuery);
         PreparedStatement checkOtherStudentsStmt = connection.prepareStatement(checkOtherStudentsQuery);
         PreparedStatement deleteCourseStmt = connection.prepareStatement(deleteCourseQuery)) {

        // Get the course_id associated with the student
        getCourseStmt.setInt(1, studentId);
        ResultSet rs = getCourseStmt.executeQuery();
        
        if (rs.next()) {
            int courseId = rs.getInt("course_id");

            // Delete the student
            deleteStudentStmt.setInt(1, studentId);
            deleteStudentStmt.executeUpdate();
            System.out.println("Student deleted successfully!");

            // Check if there are other students associated with the same course
            checkOtherStudentsStmt.setInt(1, courseId);
            ResultSet courseRs = checkOtherStudentsStmt.executeQuery();

            if (courseRs.next() && courseRs.getInt(1) == 0) {
                // If no other students are enrolled in the course, delete the course
                deleteCourseStmt.setInt(1, courseId);
                deleteCourseStmt.executeUpdate();
                System.out.println("Course deleted successfully because no other students are enrolled.");
            }
        } else {
            System.out.println("Student not found.");
        }
    }
    
    
}
    public static void getStudentsByCourse(String courseName) throws SQLException {
    String query = "SELECT students.student_id, students.student_name, students.student_email, " +
                   "courses.course_id, courses.course_name, courses.course_code, courses.course_duration " +
                   "FROM students " +
                   "JOIN courses ON students.course_id = courses.course_id " +
                   "WHERE LOWER(courses.course_name) = LOWER(?)";

    try (Connection connection = Databaseconne.getConnection();
         PreparedStatement stmt = connection.prepareStatement(query)) {

        // Trim the input to ensure no leading/trailing spaces
        courseName = courseName.trim();

        // Set the course name parameter in the query
        stmt.setString(1, courseName.toLowerCase());

        // Execute the query and check if it's working
        ResultSet rs = stmt.executeQuery();

        boolean found = false;

        // Print the table header
        System.out.printf("%-12s %-20s %-30s %-10s %-20s %-15s %-15s%n", 
                          "Student ID", "Student Name", "Student Email", "Course ID", 
                          "Course Name", "Course Code", "Course Duration");

        // Print a line separator
        System.out.println("--------------------------------------------------------------");

        // Iterate through the result set and display student data
        while (rs.next()) {
            found = true;
            System.out.printf("%-12d %-20s %-30s %-10s %-20s %-15d %-15d%n", 
                              rs.getInt("student_id"),
                              rs.getString("student_name"),
                              rs.getString("student_email"),
                              rs.getString("course_id"),
                              rs.getString("course_name"),
                              rs.getInt("course_code"),
                              rs.getInt("course_duration"));
        }

        if (!found) {
            System.out.println("No students found for the course: " + courseName);
        }

    } catch (SQLException e) {
        System.out.println("Error in executing query: " + e.getMessage());
    }
}
    public static void changeStudentPassword(int studentId, String newPassword) {
        String updateQuery = "UPDATE students SET password = ? WHERE student_id = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_course_management", "root", "");
             PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setString(1, newPassword);
            statement.setInt(2, studentId);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Password updated successfully");
            } else {
                System.out.println("Error updating password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void viewStudentDetails(int studentId) throws SQLException {
    String query = "SELECT students.student_id, students.student_name, students.student_email, " +
                   "courses.course_id, courses.course_name, courses.course_code, courses.course_duration " +
                   "FROM students " +
                   "JOIN courses ON students.course_id = courses.course_id " +
                   "WHERE students.student_id = ?";

    try (Connection connection = Databaseconne.getConnection();
         PreparedStatement stmt = connection.prepareStatement(query)) {

        // Set the studentId parameter in the query
        stmt.setInt(1, studentId);

        // Execute the query
        ResultSet rs = stmt.executeQuery();

        // Check if student record is found
        if (rs.next()) {
            // Print student details
            System.out.printf("%-15s %-25s %-30s %-10s %-25s %-15s %-15s%n", 
                              "Student ID", "Student Name", "Student Email", 
                              "Course ID", "Course Name", "Course Code", "Course Duration");
            System.out.println("-----------------------------------------------------------------------------------------------");
            System.out.printf("%-15d %-25s %-30s %-10d %-25s %-15d %-15d%n", 
                              rs.getInt("student_id"), rs.getString("student_name"), 
                              rs.getString("student_email"), rs.getInt("course_id"), 
                              rs.getString("course_name"), rs.getInt("course_code"), 
                              rs.getInt("course_duration"));
        } else {
            System.out.println("Student not found with ID: " + studentId);
        }
    }
}
    
    
}
