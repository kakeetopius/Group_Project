/*
to deal with all logic
concerning enrolling students
to courses and getting statistics
about courses, students
and lecturers from database
plus checking if lec, student or
course exists in database.
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class EnrollmentDAO extends DBCon{
    private final Connection con = super.getConnection();
    private final CourseDAO courseDAO = new CourseDAO();
    private final StudentDAO studentDAO = new StudentDAO();

    //only adds to the database not to object.
    public int addStudenttoCourse(Student student, Course course) {
        String coursename = course.getCoursename();
        int stdid = student.getStdid();
        int courseid = course.getCourseid();
        if (!checkifStudentExists(stdid)) {
            System.out.println("Student doesn't exist in the database");
            return -1;
        }
        else if (!checkifCourseExists(coursename)) {
            System.out.println("Course doesn't exist in Database");
            return -1;
        }

        String query = "INSERT INTO enrollment(courseid, stdid) VALUES(?,?)";
        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1,courseid);
            stmt.setInt(2,stdid);
            int affected_rows = stmt.executeUpdate();
            if (affected_rows == 1) {
                System.out.println("Student added to Course in the database");
                return 0;
            }
            else {
                System.out.println("Could not add student to Course in the database");
                return -1;
            }
        }
        catch (SQLException e) {
            System.out.println("Error adding student to Course :" + e.getMessage());
            return -1;
        }
    }


    //only adds to the Database not lecturer object.
    public int assignLectoCourse(Lecturer lec, Course course) {
        int lecid = lec.getLecid();
        int courseid = course.getCourseid();
        if (!checkifCourseExists(course.getCoursename())){
            System.out.println("Course doesn't exist in Database");
            return -1;
        }
        else if (!checkifLectureExists(lecid)){
            System.out.println("Lecture doesn't exist in Database");
            return -1;
        }

        String query = "UPDATE course SET lecid = ? WHERE courseid = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1,lecid);
            stmt.setInt(2,courseid);
            int affected_rows = stmt.executeUpdate();
            if (affected_rows == 1) {
                System.out.println("Lecture assigned to Course in the database");
                return 0;
            }
            else {
                System.out.println("Could not assign lecture to Course in the database");
                return -1;
            }
        }
        catch (SQLException e) {
            System.out.println("Error assigning lecture to Course :" + e.getMessage());
            return -1;
        }
    }

    //adds courses student has enrolled to from the database to student object.
    public void getCoursesForStudent(Student student) {
        int stdid = student.getStdid();
        String query = "SELECT courseid FROM course NATURAL JOIN enrollment where stdid=?";
        try{
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1,stdid);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                int courseid = rs.getInt("courseid");
                student.addCourse(courseDAO.getCoursebyId(courseid));  //get course id then get course object and add it to Student object course array
            }
        }
        catch (SQLException e) {
            System.out.println("Error setting courses for student :" + e.getMessage());
        }
    }

    public void getCourseForLecture(Lecturer lec) {
        int lecid = lec.getLecid();
        String query = "SELECT courseid FROM course WHERE lecid = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1,lecid);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                int courseid = rs.getInt("courseid");
                lec.setCourse(courseDAO.getCoursebyId(courseid)); //get course object and add to leccturer object
            }
            else {
                System.out.println("Could not find course for lecturer");
            }
        }
        catch (SQLException e) {
            System.out.println("Error setting courses for lecturer :" + e.getMessage());
        }
    }

    public void getStudentsinCourse(Course course) {
        int courseid = course.getCourseid();
        ArrayList<Student> students = new ArrayList<>();

        String query = "SELECT stdid from enrollment where courseid = ?";

        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1,courseid);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                int stdid = rs.getInt("stdid");
                Student student = studentDAO.getStudentByID(stdid); //get student object using id
                students.add(student);
            }
        }
        catch (SQLException e) {
            System.out.println("Error setting students in Course :" + e.getMessage());
        }

        course.setStudents(students);
    }

    //adds a mark to the database for the student
    public int assignMarktoStudent(Student student, Course course, int marktoassign) {
        int stdid = student.getStdid();
        int courseid = course.getCourseid();
        String query = "UPDATE enrollment SET grade = ? WHERE courseid = ? and stdid = ?";

        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1,marktoassign);
            stmt.setInt(2,courseid);
            stmt.setInt(3,stdid);
            int affected_rows = stmt.executeUpdate();
            if (affected_rows == 1) {
                System.out.println("Mark assigned to Student in the database");
                return 0;
            }
            else {
                System.out.println("Could not assign mark to Student in the database");
                return -1;
            }
        }
        catch (SQLException e) {
            System.out.println("Error assigning mark to Student :" + e.getMessage());
            return -1;
        }

    }


    public void getMarksforStudent(Student student) {
        Map<String, Integer> marks = new HashMap<>();

        int stdid = student.getStdid();

        String query = "SELECT coursename, grade FROM enrollment NATURAL JOIN course WHERE stdid = ?";

        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1,stdid);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String coursename = rs.getString("coursename");
                int mark = rs.getInt("grade");
                marks.put(coursename, mark);
            }
        }
        catch (SQLException e) {
            System.out.println("Error setting marks for student :" + e.getMessage());
        }

        student.setGrades(marks); //add marks set to student object
    }


    public void getMarksforCourse(Course course) {
        Map<String, Integer> courseMarks = new HashMap<>();
        int courseid = course.getCourseid();

        String query = "SELECT fname, lname, grade FROM enrollment NATURAL JOIN student WHERE courseid = ?";

        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1,courseid);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String fname = rs.getString("fname");
                String lname = rs.getString("lname");
                int mark = rs.getInt("grade");
                String fullname = fname + " " + lname;
                courseMarks.put(fullname, mark);
            }
        }
        catch (SQLException e) {
            System.out.println("Error setting marks for course :" + e.getMessage());
        }

        course.setGrades(courseMarks);
    }

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
