/*
interacts with the student
and lecturer table based on
email plus also updating email and
password.
 */

import java.sql.*;

public class PersonDAO extends  DBCon{
    protected Connection con = super.getConnection();

    public Person getPersonByEmail(String email){
        Person person = null;
        String query = "SELECT * FROM student WHERE email = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            //checking if there is a result
            if(rs.next()) {
                //getting student Values
                int stdid = rs.getInt(1);
                String fname = rs.getString(2);
                String lname = rs.getString(3);
                String gender = rs.getString(8);
                String password = rs.getString(9);

                //creating Student values and setting values
                person = new Student(fname,lname,gender,email);
                ((Student)person).setStdid(stdid);
                person.setPassword(password);
            }
            //if no student found
            else {
                query = "SELECT * FROM lecturer WHERE email = ?";
                stmt = con.prepareStatement(query);
                stmt.setString(1, email);
                rs = stmt.executeQuery();
                if(rs.next()) {
                    //getting details about the Lecturer
                    int lecid = rs.getInt(1);
                    String fname = rs.getString(2);
                    String lname = rs.getString(3);
                    String gender = rs.getString(8);
                    String password = rs.getString(9);
                    String dept = rs.getString(7);

                    //creating Lecturer object and assigning values
                    person = new Lecturer(fname,lname,gender,email,dept);
                    ((Lecturer)person).setLecid(lecid);
                    person.setPassword(password);
                }
                //if no student or lecturer found
                else {
                    System.out.println("No user found for email " + email);
                }
            }
        }
        catch (SQLException e) {
            System.out.println("SQL ERROR: " + e.getMessage());
        }
        return person;
    }

    public int updatePassword(Person person, String new_password) {
        String query = "";

        if (person instanceof Student) {
            query = "UPDATE student SET password = ? WHERE stdid = ?";
        }
        else if (person instanceof Lecturer) {
            query = "UPDATE lecturer SET password = ? WHERE lecid = ?";
        }

        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1,new_password);
            if (person instanceof Student) {
                stmt.setInt(2,((Student)person).getStdid());
            }
            if (person instanceof Lecturer) {
                stmt.setInt(2,((Lecturer)person).getLecid());
            }

            int affected_rows = stmt.executeUpdate();
            if (affected_rows == 1){
                System.out.println("Successfully updated password");
            }
            else {
                System.out.println("Failed to update password");
            }
            return 0;
        }
        catch (SQLException e){
            System.out.println("Error updating password");
            return -1;
        }
    }

    public int updateEmail(Person person, String new_email) {
        String query = "";
        if (person instanceof Student) {
            query = "UPDATE student SET email = ? WHERE stdid = ?";
        }
        else if (person instanceof Lecturer) {
            query = "UPDATE lecturer SET email = ? WHERE lecid = ?";
        }
        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1,new_email);
            if (person instanceof Student) {
                stmt.setInt(2,((Student)person).getStdid());
            }
            else if (person instanceof Lecturer) {
                stmt.setInt(2,((Lecturer)person).getLecid());
            }

            int affected_rows = stmt.executeUpdate();
            if (affected_rows == 1){
                System.out.println("Successfully updated email");
            }
            else {
                System.out.println("Failed to update email");
            }
            return 0;
        }
        catch (SQLException e){
            System.out.println("Error updating email");
            return -1;
        }
    }
}
