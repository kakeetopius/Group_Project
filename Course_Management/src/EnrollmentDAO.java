/* to deal with all logic
concerning enrolling students
to courses and getting statistics
about enrollments from database
 */

import java.sql.*;
import java.util.ArrayList;

public class EnrollmentDAO extends DBCon{
    private Connection con = super.getConnection();
    private StudentDAO studentDAO = new StudentDAO();
    private LecturerDAO lecturerDAO = new LecturerDAO();

    /*public int addStudenttoCourse(int stdid, int courseid) {

    }

    public int assignLectoCourse(int lecid, int courseid) {

    }

    public void setCoursesForStudent(int stdid) {

    }

    public void setCoursesForLecture(int lecid) {

    }

    public ArrayList<Integer> getMarksforStudent(int stdid) {

    }

    public ArrayList<Integer> getMarksforCourse(String coursename) {

    }*/

    public boolean checkifCourseExists(String coursename) {
        String query = "SELECT EXISTS (SELECT * FROM course where coursename = ?)";
        boolean exists = false;

        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, coursename);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                exists = rs.getBoolean(1);
            }
        }
        catch (SQLException e) {
            System.out.println("Error in checking if course exists" + e.getMessage());
        }
        return exists;
    }

    public boolean checkifStudentExists(int stdid) {
        String query = "SELECT EXISTS (SELECT * FROM student where stdid = ?)";
        boolean exists = false;

        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1,stdid);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                exists = rs.getBoolean(1);
            }
        }
        catch (SQLException e) {
            System.out.println("Error in checkifStudentExists" + e.getMessage());
        }
        return exists;
    }

    public boolean checkifLectureExists(int lecid) {
        String query = "SELECT EXISTS (SELECT * FROM lecturer where lecid = ?)";
        boolean exists = false;

        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1,lecid);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                exists = rs.getBoolean(1);
            }
        }
        catch (SQLException e) {
            System.out.println("Error in checkifLecturerExists" + e.getMessage());
        }
        return exists;
    }


}
