/*
For establishing
a connection with database.
 */


import java.sql.*;
import java.io.*;
import java.util.Scanner;

public class DBCon {
    final String url = "jdbc:postgresql://localhost:5432/course_mgmt";
    private String user = "";
    private String password = "";
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

    private void getCredentials(String filename) {
        StringBuilder sb = new StringBuilder();
        File file = new File(filename);

        try {
            Scanner sc = new Scanner(file);
            while(sc.hasNext()) {
                sb.append(sc.nextLine());
                sb.append("\n");
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

        String creds = sb.toString();

        String[] rows = creds.split("\n");

        for (String row : rows) {
            String[] values = row.split(":");
            this.user = values[0];
            this.password = values[1];
        }
    }
}
