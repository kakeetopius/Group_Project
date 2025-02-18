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
    private final CourseDAO courseDAO = new CourseDAO();
    private final StudentDAO studentDAO = new StudentDAO();

    //only adds to the database not to object.
    public int addStudenttoCourse(int studentID, int courseID) {
        String query = String.format("INSERT INTO enrollment(courseid, stdid)VALUES(%d, %d)", courseID, studentID);

        int status = super.insertData(query);

        if (status == 0) {
            System.out.println("Student successfully added to course");
        }
        else {
            System.out.println("Student could not be added to course");
        }
        return status;
    }

    //only adds to the Database not lecturer object.
    public int assignLectoCourse(int lecID, int courseID) {
        String query = String.format("UPDATE course SET lecid = %d WHERE courseid = %d", lecID, courseID);

        int status = super.insertData(query);
        if (status == 0) {
            System.out.println("Lecture successfully assigned to course");
        }
        else {
            System.out.println("Lecture could not be assigned to course");
        }
        return status;
    }

    //adds courses student has enrolled to from the database to student object.
    public ArrayList<Course> getCoursesForStudent(int stdid) {
        ArrayList<Course> courses = null;

        String query = String.format("SELECT courseid FROM course NATURAL JOIN enrollment WHERE stdid = %d", stdid);
        try{
            ResultSet rs = super.getData(query);
            courses = new ArrayList<>();
            while(rs.next()) {
                int courseid = rs.getInt("courseid");
                courses.add(courseDAO.getCoursebyId(courseid));
            }
        }
        catch (SQLException e) {
            System.out.println("Error setting courses for student :" + e.getMessage());
        }
        return courses;
    }

    public Course getCourseForLecturer(int lecid) {
        Course course = null;
        String query = String.format("SELECT courseid FROM course WHERE lecid = %d", lecid);
        try {
            ResultSet rs = super.getData(query);
            if(rs.next()) {
                int courseid = rs.getInt("courseid");
                course = courseDAO.getCoursebyId(courseid);
            }
            else {
                System.out.println("Could not find course for lecturer");
            }
        }
        catch (SQLException e) {
            System.out.println("Error setting courses for lecturer :" + e.getMessage());
        }
        return course;
    }

    public ArrayList<Student> getStudentsinCourse(int courseid) {
        ArrayList<Student> students = null;

        String query = String.format("SELECT stdid FROM enrollment WHERE courseid = %d", courseid);

        try {
            ResultSet rs = super.getData(query);
            students = new ArrayList<>();
            while(rs.next()) {
                int stdid = rs.getInt("stdid");
                Student student = studentDAO.getStudentByID(stdid); //get student object using id
                students.add(student);
            }
        }
        catch (SQLException e) {
            System.out.println("Error setting students in Course :" + e.getMessage());
        }
        return students;
    }

    public Lecturer getLecforCourse (int courseid) {
        String query = String.format("SELECT lecid FROM course WHERE courseid = %d", courseid);
        Lecturer lec = null;

        try {
            ResultSet rs = super.getData(query);
            if (rs.next()){
                int lecid = rs.getInt("lecid");
                lec = new LecturerDAO().getLecturerByID(lecid);
            }
        }
        catch (SQLException e) {
            System.out.println("Error setting lectures in Course :" + e.getMessage());
        }
        return lec;
    }
    //adds a mark to the database for the student
    public int assignMarktoStudent(int studentID, int courseID, int marktoassign) {
        String query = String.format("UPDATE enrollment SET grade = %d WHERE courseid = %d AND stdid = %d", marktoassign, courseID, studentID);

        int status = super.insertData(query);
        if (status == 0) {
            System.out.println("Mark successfully assigned to student");
        }
        else {
            System.out.println("Mark could not be assigned to student");
        }
        return status;
    }


    public Map<String,Integer> getMarksforStudent(int stdid) {
        Map<String, Integer> marks = null;

        String query = String.format("SELECT coursename, grade FROM enrollment NATURAL JOIN course WHERE stdid=%d",stdid);
        try {
            ResultSet rs = super.getData(query);
            marks = new HashMap<>();
            while (rs.next()) {
                String coursename = rs.getString("coursename");
                int mark = rs.getInt("grade");
                marks.put(coursename, mark);
            }
        }
        catch (SQLException e) {
            System.out.println("Error setting marks for student :" + e.getMessage());
        }
        return marks;
    }


    public Map<String, Integer> getMarksforCourse(int courseid) {
        Map<String, Integer> courseMarks = null;
        String query = String.format("SELECT fname, lname, grade FROM enrollment NATURAL JOIN student WHERE courseid = %d",courseid);

        try {
            ResultSet rs = super.getData(query);
            courseMarks = new HashMap<>();
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

        return courseMarks;
    }

    public boolean checkifCourseExists(String coursename) {
        String query = String.format("SELECT EXISTS (SELECT * FROM course WHERE coursename = '%s')",coursename);
        boolean exists = false;

        ResultSet rs = super.getData(query);
        try {
            if (rs.next()){
                exists = rs.getBoolean(1);
            }
        }
        catch (SQLException e) {
            System.out.println("Error checking if course exists :" + e.getMessage());
        }
        return exists;
    }

    public boolean checkifStudentExists(int stdid) {
        String query = String.format("SELECT EXISTS (SELECT * FROM course WHERE coursename = %d)",stdid);
        boolean exists = false;

        ResultSet rs = super.getData(query);
        try {
            if (rs.next()){
                exists = rs.getBoolean(1);
            }
        }
        catch (SQLException e) {
            System.out.println("Error checking if student :" + e.getMessage());
        }
        return exists;
    }

    public boolean checkifLectureExists(int lecid) {
        String query = String.format("SELECT EXISTS (SELECT * FROM course WHERE coursename = %d)",lecid);
        boolean exists = false;

        ResultSet rs = super.getData(query);
        try {
            if (rs.next()){
                exists = rs.getBoolean(1);
            }
        }
        catch (SQLException e) {
            System.out.println("Error checking if lecturer exists :" + e.getMessage());
        }
        return exists;
    }
}
