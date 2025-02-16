import java.sql.*;

public class CourseDAO extends DBCon{
    private Connection con = super.getConnection();

    public int addCourse(Course course) {
        String query = "INSERT INTO course(coursename,credit) VALUES(?,?)";
        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1,course.getCoursename());
            stmt.setInt(2, course.getCredit());
            int affected_rows = stmt.executeUpdate();

            if (affected_rows == 1) {
                System.out.println("Course Added Successfully");
                return 0;
            }
            else {
                System.out.println("Could not add course");
                return -1;
            }
        }
        catch (SQLException e) {
            System.out.println("Error In seting Course: " + e.getMessage());
            return -1;
        }
    }

    public Course getCoursebyId(int courseid) {
        String query = "SELECT * FROM course WHERE courseid = ?";
        Course course = null;

        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1,courseid);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String coursename = rs.getString("coursename");
                int credit = rs.getInt("credit");
                int cid = rs.getInt("courseid");
                course = new Course(coursename,credit);
                course.setCourseid(cid);
            }
            else {
                System.out.println("Course Not Found");
            }
        }
        catch (SQLException e) {
            System.out.println("Error In getting Course: " + e.getMessage());
        }
        return course;
    }

    public Course getCoursebyName(String coursename) {
        String query = "SELECT * FROM course WHERE coursename = ?";
        Course course = null;
        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1,coursename);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int courseid = rs.getInt("courseid");
                int credit = rs.getInt("credit");
                course = new Course(coursename,credit);
                course.setCourseid(courseid);
            }
            else {
                System.out.println("Course Not Found");
            }
        }
        catch (SQLException e) {
            System.out.println("Error In getting Course: " + e.getMessage());
        }
        return course;
    }

    public int deleteCourseByID(int courseid) {
        String query = "DELETE FROM course WHERE courseid = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, courseid);
            int deleted_rows = stmt.executeUpdate();
            if (deleted_rows == 1) {
                System.out.println("Course Deleted Successfully");
                return 0;
            }
            else {
                System.out.println("Could not delete course");
                return -1;
            }
        }
        catch (SQLException e) {
            System.out.println("Error In deleting Course: " + e.getMessage());
            return -1;
        }
    }
}

