package DBAccess;/*
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


    public DBCon() {
        File creds = new File("/home/pius/Dev/Projects/Course_Management_System/config/default_credentials.txt");
        if (!creds.exists()) {
            setCredentials();
        }
        getCredentials();
    }

    private Connection getConnection() {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, user, password);
        }
        catch(SQLException e) {
            System.out.println("Error Accessing Database");
            File creds = new File("/home/pius/Dev/Projects/Course_Management_System/config/default_credentials.txt");

            if (creds.exists()) {
                creds.delete();
		System.out.printf("Credentials given\nUser: %s\nPassword;%s\n", this.user, this.password);
                System.out.println("Check credentials");
            }
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return conn;
    }

    protected int insertData(String query) {
        Connection con = this.getConnection();

        try {
            Statement stmt = con.createStatement();
            PreparedStatement stm = con.prepareStatement(query);
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
        File file = new File("/home/pius/Dev/Projects/Course_Management_System/config/default_credentials.txt");

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
        if (rows.length != 2) {
            System.out.println("Error getting credentials.");
            return;
        }

        String[] credentials = new String[2]; //for username and password

        for (int i = 0 ; i < rows.length; i++) {
            String[] values = rows[i].split(":");  //separates value from label from each row
            if (values.length != 2) {
                System.out.println("Error getting credentials");
                return;
            }
            credentials[i] = values[1].strip(); //extracts the value to initialise the credentials array
        }
        this.user = credentials[0];
        this.password = credentials[1];
    }

    public static void setCredentials() {
        Scanner sc = new Scanner(System.in);
        try {
            FileWriter fw = new FileWriter("/home/pius/Dev/Projects/Course_Management_System/config/default_credentials.txt");
            System.out.println("Enter database username: ");
            String name = sc.nextLine();
            System.out.println("Enter password: ");
            String pass = sc.nextLine();

            String writing = "User:" + name + "\n" + "Password:" + pass + "\n";

            fw.write(writing);
            fw.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getCountOfTable(String table) {
        String query = String.format("SELECT COUNT(*) FROM %s", table);
        ResultSet rs = this.getData(query);

        int count = -1;
        try {
            if (rs.next()){
                count = rs.getInt(1);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return count;
    }

}
