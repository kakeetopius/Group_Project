package DBAccess;/*
to deal with
interacting student
table in the database.
 */


import BaseClasses.Student;

import java.sql.*;

public class StudentDAO extends PersonDAO {

    public int addStudent(Student student) {
        String query = String.format("INSERT INTO student(fname,lname,gender,email) VALUES('%s','%s','%s','%s')",student.getfname(),student.getlname(),student.getGender(),student.getemail());
        int status = super.insertData(query);

        if (status == 0) {
            System.out.println("BaseClasses.Student added successfully");
        } else {
            System.out.println("failed to add student");
        }
        return status;
    }

    public int getStudentID(String email) {
        String query = String.format("SELECT stdid FROM student WHERE email = '%s'", email);

        ResultSet rs = super.getData(query);
        try{
            if (rs.next()){
                int id = rs.getInt("stdid");
                return id;
            }
        } catch (SQLException e) {
            System.out.println("Error getting studentId " + e.getMessage());
        }
        return -1;
    }

    public Student getStudentByID(int id) {
        Student student = null;
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
                String password = rs.getString("password");

                student = new Student(fname, lname, gender, email);
                student.setPassword(password);
            } else {
                System.out.println("BaseClasses.Student not found");
            }
        } catch (SQLException e) {
            System.out.println("Error Getting BaseClasses.Student: " + e.getMessage());
        }

        return student;
    }


    public int deleteStudentById(int studid) {
        String query = "DELETE FROM student WHERE stdid = " + studid;

        int status = super.insertData(query);
        if (status == 0) {
            System.out.println("BaseClasses.Student successfully deleted");
        } else {
            System.out.println("BaseClasses.Student not found. Could not delete");
        }
        return status;
    }
}



