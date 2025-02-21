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
        getCredentials();
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

    private void getCredentials() {
        StringBuilder sb = new StringBuilder();  //for building text from credential file.
        File file = new File("credentials.txt");

        try {
            Scanner sc = new Scanner(file);
            //getting text from file.
            while(sc.hasNext()) {
                sb.append(sc.nextLine());
                sb.append("\n");
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

        String textString = sb.toString(); //converting string builder text to string

        String[] rows = textString.split("\n");  //splits the text into an array of rows from file
        String name = "";
        String pass = "";

        String[] credentials = new String[2]; //for username and password

        for (int i = 0 ; i < rows.length; i++) {
            String[] values = rows[i].split(":");  //separates value from label from each row
            credentials[i] = values[1]; //extracts the value to initialise the credentials array
        }
        this.user = credentials[0];
        this.password = credentials[1];
    }

}
