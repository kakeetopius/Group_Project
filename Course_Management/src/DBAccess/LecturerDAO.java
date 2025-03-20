package DBAccess;/*
to deal with
the BaseClasses.Lecturer table
in the database.
 */

import BaseClasses.Lecturer;

import java.sql.*;
import java.util.ArrayList;

public class LecturerDAO extends PersonDAO {
    private  Lecturer lec = null;

    public int addLecturer(Lecturer lec) {
        //Getting first letter of gender string
        String gender = String.valueOf(lec.getGender().charAt(0));
        String query = String.format("INSERT INTO lecturer(fname,lname,gender,email,dept,dob,phone) VALUES('%s','%s','%s','%s','%s','%s','%s')", lec.getfname(), lec.getlname(), gender, lec.getemail(), lec.getDept(),lec.getdob(),lec.getphone());

        int status = super.insertData(query);
        if (status == 0) {
            System.out.println("Lecturer added successfully");
        } else {
            System.out.println("Lecturer not added");
        }
        return status;
    }

    public ArrayList<Integer> getAllLecIDs() {
        //List to store all ids for lecturers.
        ArrayList<Integer> lecids = new ArrayList<>();

        String query = "SELECT lecid FROM lecturer";
        int id;
        try {
            ResultSet rs = super.getData(query);
            while (rs.next()) {
                id = rs.getInt("lecid");
                lecids.add(id);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return lecids;
    }

    public int getLecturerID(String email) {
        //function gets lecturer id from the database using the email.
        String query = String.format("SELECT lecid FROM lecturer WHERE email = '%s'", email);

        ResultSet rs = super.getData(query);
        try {
            if (rs.next()) {
                int id = rs.getInt("lecid");
                return id;
            }
        } catch (SQLException e) {
            System.out.println("Error getting studentId " + e.getMessage());
        }
        return -1;
    }

    public Lecturer getLecturerByID(int id) {
        //Function creates and returns a lecturer object using the lecturer id.
        String query = String.format("SELECT * FROM lecturer WHERE lecid=%d", id);

        ResultSet rs = super.getData(query);

        try {
            if (rs.next()) {
                int lecid = rs.getInt("lecid");
                String fname = rs.getString("fname");
                String lname = rs.getString("lname");
                String email = rs.getString("email");
                String dept = rs.getString("dept");
                String gender = rs.getString("gender");
                //---Creating lecturer object----------
                lec = new Lecturer(fname, lname, gender, email, dept);
            } else {
                System.out.println("Lecturer not found");
            }
        } catch (Exception e) {
            System.out.println("Error getting lecturer :" + e.getMessage());
        }
        return lec;
    }


    public int deleteLecturerById(int lecid) {
        String query = String.format("DELETE FROM lecturer WHERE lecid=%d", lecid);
        int status = super.insertData(query);
        if (status == 0) {
            System.out.println("BaseClasses.Lecturer deleted successfully");
        } else {
            System.out.println("Error: BaseClasses.Lecturer not deleted");
        }
        return status;
    }

    public int editLecturerField(String fieldname, String value, int lecturerid) {
        String query;
        String[] validFieldNames = {"fname","lname","email","phone","dob","dept","gender","password"};

        //=========checking if fieldname is valid============
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
        query = String.format("UPDATE lecturer SET %s = '%s' WHERE stdid = %d", fieldname,value,lecturerid);

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

    public int getNumberOfLecturers() {
        return super.getCountOfTable("lecturer");
    }
}



