/*
to deal with
the Lecturer table
in the database.
 */

import java.sql.*;

public class LecturerDAO extends PersonDAO {

    public int addLecturer(Lecturer lec) {
        String query = String.format("INSERT INTO lecturer(fname,lname,gender,email,dept) VALUES('%s','%s','%s','%s','%s')",lec.getfname(),lec.getlname(),lec.getGender(),lec.getemail(),lec.getDept());

        int status = super.insertData(query);
        if (status == 0) {
            System.out.println("Lecturer added successfully");
        } else {
            System.out.println("Error: Lecturer not added");
        }
        return status;
    }

    public int getLecturerID(String email) {
        String query = String.format("SELECT lecid FROM lecturer WHERE email = '%s'", email);

        ResultSet rs = super.getData(query);
        try{
            if (rs.next()){
                int id = rs.getInt("lecid");
                return id;
            }
        } catch (SQLException e) {
            System.out.println("Error getting studentId " + e.getMessage());
        }
        return -1;
    }

    public Lecturer getLecturerByID(int id) {
        Lecturer lec = null;
        String query = String.format("SELECT * FROM lecturer WHERE lecid=%d",id);

        ResultSet rs = super.getData(query);

        try {
            if (rs.next()) {
                int lecid = rs.getInt("lecid");
                String fname = rs.getString("fname");
                String lname = rs.getString("lname");
                String email = rs.getString("email");
                String dept = rs.getString("dept");
                String gender = rs.getString("gender");
                String password = rs.getString("password");
                lec = new Lecturer(fname, lname, gender, email, dept);
                lec.setPassword(password);
            } else {
                System.out.println("Lecturer not found");
            }
        } catch (Exception e) {
            System.out.println("Error getting lecturer :" + e.getMessage());
        }
        return lec;
    }


    public int deleteLecturerById(int lecid) {
        String query = String.format("DELETE FROM lecturer WHERE lecid=%d",lecid);
        int status = super.insertData(query);
        if (status == 0) {
            System.out.println("Lecturer deleted successfully");
        }
        else {
            System.out.println("Error: Lecturer not deleted");
        }
        return status;
    }
}



