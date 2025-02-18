/*
For establishing
a connection with database.
 */


import java.sql.*;

public class DBCon {
    final String url = "jdbc:postgresql://localhost:5432/course_mgmt";
    final String user = "postgres";
    final String password = "kapila";

    public DBCon() {

    }

    public Connection getConnection(){
        Connection con = null;
        try {
            con = DriverManager.getConnection(url, user, password);
        }
        catch (SQLException e) {
            System.out.println("Error Connecting to database: " + e.getMessage());
        }

        return con;
    }

    public int insertData(String query) {
        Connection con = getConnection();
        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.executeUpdate();
            con.close();
            return 0;
        }
        catch (SQLException e) {
            System.out.println("Error Connecting to database: " + e.getMessage());
            try {
                con.close();
            }
            catch (SQLException e1) {
                System.out.println("Error Closing database connection: " + e1.getMessage());
            }
            return -1;
        }
    }

    public ResultSet getData(String query) {
        Connection con = getConnection();
        ResultSet rs = null;

        try {
            PreparedStatement stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
            con.close();
        }
        catch (SQLException e) {
            System.out.println("Error Connecting to database: " + e.getMessage());
        }
        return rs;
    }
}
