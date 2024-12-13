package SMS;
import java.sql.*;

public class Databaseconne {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/student_course_management";
        String user = "root"; // Your MySQL username
        String password = ""; // Your MySQL password

        return DriverManager.getConnection(url, user, password);
    }
}
