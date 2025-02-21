import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.io.*;

public class Main{
    public static void main(String[] args) throws IOException {
        File creds = new File("credentials.txt");

        if (!creds.exists()) {
            setCredentials();
        }

    }


    public static void setCredentials() {
        Scanner sc = new Scanner(System.in);
        try {
            FileWriter fw = new FileWriter("credentials.txt");
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

}