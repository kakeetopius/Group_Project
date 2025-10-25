package DBAccess;
/*
to deal with
interacting student
table in the database.
 */


import BaseClasses.Student;

import java.sql.*;
import java.util.ArrayList;

public class StudentDAO extends PersonDAO {
    private Student student = null;

    public int addStudent(Student student) {
        //extracting the first letter of  the gender string.
        String gender = String.valueOf(student.getGender().charAt(0));
        String query = String.format("INSERT INTO student(fname,lname,gender,email,phone,dob) VALUES('%s','%s','%s','%s','%s','%s')", student.getfname(), student.getlname(), gender, student.getemail(), student.getphone(), student.getdob());
        int status = super.insertData(query);

        //if successfull
        if (status == 0) {
            System.out.println("Student added successfully");
        } else {
            System.out.println("failed to add student");
        }
        return status;
    }


    public ArrayList<Integer> getAllStudentIDs() {
        // a list to store all student ids from the database.
        ArrayList<Integer> stdids = new ArrayList<>();

        String query = "SELECT stdid FROM student ORDER BY(stdid)";
        int id;

        try {
            ResultSet rs = super.getData(query);
            while (rs.next()) {
                id = rs.getInt("stdid");
                stdids.add(id);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return stdids;
    }

    public int getStudentID(String email) {
        String query = String.format("SELECT stdid FROM student WHERE email = '%s'", email);

        ResultSet rs = super.getData(query);
        try {
            if (rs.next()) {
                int id = rs.getInt("stdid");
                return id;
            }
        } catch (SQLException e) {
            System.out.println("Error getting studentId " + e.getMessage());
        }
        return -1;
    }

    public Student getStudentByID(int id) {
        //====The function returns a student object from the database using the student id.
        String query = "SELECT * FROM student WHERE stdid = " + id;

        try {
            ResultSet rs = super.getData(query);
            //checking if there is a result
            if (rs.next()) {
                int stdid = rs.getInt("stdid");
                String fname = rs.getString("fname");
                String lname = rs.getString("lname");
                String email = rs.getString("email");
                String gender = rs.getString("gender");
                //===Creating a student object===========
                student = new Student(fname, lname, gender, email);
            } else {
                System.out.println("Student not found");
            }
        } catch (SQLException e) {
            System.out.println("Error Getting Student: " + e.getMessage());
        }

        return student;
    }


    public int deleteStudentById(int studid) {
        String query = "DELETE FROM student WHERE stdid = " + studid;

        int status = super.insertData(query);
        if (status == 0) {
            System.out.println("Student successfully deleted");
        } else {
            System.out.println("Student not found. Could not delete");
        }
        return status;
    }

    public int editStudentField(String fieldname, String value, int studentid) {
        String query;
        String[] validFieldNames = {"fname","lname","email","phone","dob","faculty","gender","password"};

        //=========checking if fieldname is in the validFieldNames============
        boolean validField = false;
        for (String validFieldName : validFieldNames) {
            if (fieldname.equals(validFieldName)) {
                validField = true;
                break;
            }
        }

        if(!validField) {
            System.out.println("Invalid fieldname");
            return -1;
        }

        //==============Creating query===================
        query = String.format("UPDATE student SET %s = '%s' WHERE stdid = %d", fieldname,value,studentid);

        try {
            int status = super.insertData(query);
            if (status == 0) {
                System.out.println("Course updated successfully");
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        }
        return 0;
    }

    public int getNumberOfStudents() {
       return super.getCountOfTable("student");
    }
}



