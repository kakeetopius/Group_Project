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

    }

    protected Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        }
        catch(SQLException e) {
            System.out.println("Error Accessing Database");
        }
        return conn;
    }

    protected int insertData(String query) {
        Connection con = this.getConnection();

        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            con.close();
            return 0;
        }
        catch(SQLException e) {
            System.out.println("Error Inserting data into the Database" + e.getMessage());
            return -1;
        }
    }


    protected ResultSet getData(String query) {
        Connection con = this.getConnection();
        ResultSet rs = null;

        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            con.close();
        } catch (SQLException e) {
            System.out.println("Error Getting data from the Database " + e.getMessage());
        }
        return rs;
    }
}
