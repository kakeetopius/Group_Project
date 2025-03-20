package DBAccess;/*
interacts with the student
and lecturer table based on
email plus also updating email and
password.
 */

import BaseClasses.Lecturer;
import BaseClasses.Person;
import BaseClasses.Student;

import java.sql.*;

public class PersonDAO extends DBCon {
    private Person person = null;

    public Person getPersonByEmail(String user_email){
        String query1 = String.format("SELECT * FROM student WHERE email = '%s'", user_email);
        String query2 = String.format("SELECT * FROM lecturer WHERE email = '%s'", user_email);


        ResultSet rs = super.getData(query1);
        try {
            //if a student with the email exists
            if (rs.next()) {
                //=====Getting student details from the database.====
                int stdid = rs.getInt("stdid");
                String fname = rs.getString("fname");
                String lname = rs.getString("lname");
                String gender = rs.getString("gender");
                //===============Creating a student object using details got from the database========
                Student student = new Student(fname,lname,gender,user_email);
                return student;
            }
            //====if the result set was empty, checking the lecturer table for the email========
            else {
                ResultSet rs1 = super.getData(query2);
                if (rs1.next()) {
                    int lecid = rs1.getInt("lecid");
                    String fname = rs1.getString("fname");
                    String lname = rs1.getString("lname");
                    String gender = rs1.getString("gender");
                    String dept = rs1.getString("dept");
                    //====Creating a lecturer object using the details got from the database===========
                    Lecturer lecturer = new Lecturer(fname,lname,gender,user_email,dept);
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


}
