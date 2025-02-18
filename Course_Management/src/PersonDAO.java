/*
interacts with the student
and lecturer table based on
email plus also updating email and
password.
 */

import java.sql.*;

public class PersonDAO extends  DBCon{

    public Person getPersonByEmail(String user_email){
        String query1 = String.format("SELECT * FROM student WHERE email = '%s'", user_email);
        String query2 = String.format("SELECT * FROM lecturer WHERE email = '%s'", user_email);
        Person person = null;

        ResultSet rs = super.getData(query1);
        try {
            if (rs.next()) { //if a student with the email exists
                int stdid = rs.getInt("stdid");
                String fname = rs.getString("fname");
                String lname = rs.getString("lname");
                String gender = rs.getString("gender");
                String password = rs.getString("password");
                Student student = new Student(fname,lname,gender,user_email);
                student.setPassword(password);
                return student;
            }
            else {
                ResultSet rs1 = super.getData(query2);
                if (rs1.next()) {
                    int lecid = rs1.getInt("lecid");
                    String fname = rs1.getString("fname");
                    String lname = rs1.getString("lname");
                    String gender = rs1.getString("gender");
                    String password = rs1.getString("password");
                    String dept = rs1.getString("dept");

                    Lecturer lecturer = new Lecturer(fname,lname,gender,user_email,dept);
                    lecturer.setPassword(password);
                    return lecturer;
                }
                else {
                    System.out.println("Could not find user with email " + user_email);
                }
            }
        }
        catch (SQLException e) {
            System.out.println("Error getting person by email" + e.getMessage());
        }
        return person;
    }

    public int updatePassword(Person person, String new_password) {
        String query = "";

        if (person instanceof Student) {
            query = String.format("UPDATE student SET password = '%s' WHERE stdid = %d",new_password,((Student)person).getStdid());
        }
        else if (person instanceof Lecturer) {
            query = String.format("UPDATE lecturer SET password = '%s' WHERE stdid = %d",new_password,((Lecturer)person).getLecID());
        }


        int status = super.insertData(query);
        if (status == 0){
            System.out.println("Successfully updated password");
        }
        else {
            System.out.println("Failed to update password");
        }
        return status;
    }

    public int updateEmail(Person person, String new_email) {
        String query = "";
        if (person instanceof Student) {
            query = String.format("UPDATE student SET email = '%s' WHERE stdid = %d",new_email,((Student)person).getStdid());
        } else if (person instanceof Lecturer) {
            query = String.format("UPDATE student SET email = '%s' WHERE stdid = %d",new_email,((Lecturer)person).getLecID());
        }

        int status = super.insertData(query);

        if (status == 0){
            System.out.println("Successfully updated email");
        }
        else {
            System.out.println("Failed to update email");
        }
        return status;
    }
}
