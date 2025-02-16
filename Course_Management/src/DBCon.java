/*
For establishing
a connection with database.
 */


import java.sql.*;

public class DBCon {
    final String url = "jdbc:postgresql://localhost:5432/course_mgmt";
    final String user = "postgres";
    final String password = "kapila";
    private Connection con;

    public DBCon() {
        try {
            con = DriverManager.getConnection(url, user, password);
        }
        catch(SQLException e) {
            System.out.println("Error Accessing Database");
        }
    }

    public Connection getConnection() {
        return con;
    }
}
