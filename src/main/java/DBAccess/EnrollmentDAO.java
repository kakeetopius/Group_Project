package DBAccess;
/*
to deal with all logic
concerning enrolling students
to courses and getting statistics
about courses, students
and lecturers from database
plus checking if lec, student or
course exists in database.
 */

import BaseClasses.Course;
import BaseClasses.Lecturer;
import BaseClasses.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


public class EnrollmentDAO extends DBCon {
    private final CourseDAO courseDAO = new CourseDAO();
    private final StudentDAO studentDAO = new StudentDAO();
    private Course course = null;
    private Student student = null;
    private Lecturer lecturer = null;


    public int addStudenttoCourse(int studentID, int courseID) {
        //----function enrolls student to a course using ids of both.------
        String query = String.format("INSERT INTO enrollment(courseid, stdid)VALUES(%d, %d)", courseID, studentID);

        int status = super.insertData(query);

        if (status == 0) {
            System.out.println("BaseClasses.Student successfully added to course");
        }
        else {
            System.out.println("BaseClasses.Student could not be added to course");
        }
        return status;
    }


    public int assignLectoCourse(int lecID, int courseID) {
        //------function assigns a lecturer a course based on their ids.------------------
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


    public ArrayList<Course> getCoursesForStudent(int stdid) {
        //----Used by a student object to get all Courses the student has been enrolled to ---------

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
        //---------------Used by lecturer object to get the course the lecturer is assigned to-----------------
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
        //---------Used by to get a list of all students enrolled to a particular course------------------------
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
        //----------------Used to get the lecturer assigned to course-------------
        String query = String.format("SELECT lecid FROM course WHERE courseid = %d", courseid);

        try {
            ResultSet rs = super.getData(query);
            if (rs.next()){
                int lecid = rs.getInt("lecid");
                lecturer = new LecturerDAO().getLecturerByID(lecid);
            }
        }
        catch (SQLException e) {
            System.out.println("Error setting lectures in BaseClasses.Course :" + e.getMessage());
        }
        return lecturer;
    }


    public int assignMarktoStudent(int studentID, int courseID, int marktoassign) {
        //------------the function assigns a mark to the student in a particular course-----------------
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
        //-------------Used to get all marks for the Student from the database-----------------
        //a map to store the coursename and the corresponding mark.
        Map<String, Integer> marks = null;

        String query = String.format("SELECT coursename, grade FROM enrollment NATURAL JOIN course WHERE stdid=%d ORDER BY(grade) DESC",stdid);
        try {
            ResultSet rs = super.getData(query);
            //Linked hash map because it returns items in the order they are added.
            marks = new LinkedHashMap<>();
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


    public Map<Integer, Integer> getMarksforCourse(int courseid) {
        //-----------------Used to get marks for all students in a particular course--------------------
        //map of student ids and their corresponding marks.
        Map<Integer,Integer> courseMarks = null;
        String query = String.format("SELECT stdid, grade FROM enrollment NATURAL JOIN student WHERE courseid = %d ORDER BY(grade) DESC",courseid);

        try {
            ResultSet rs = super.getData(query);
            //Linked map to maintain the order.
            courseMarks = new LinkedHashMap<>();
            while (rs.next()) {
                int id = rs.getInt("stdid");
                int mark = rs.getInt("grade");
                courseMarks.put(id, mark);
            }
        }
        catch (SQLException e) {
            System.out.println("Error setting marks for course :" + e.getMessage());
        }

        return courseMarks;
    }

    public ArrayList<Integer> getAvailableLecturesID() {
        //----function gets all lecturers who aren't assigned a course and returns their ids as a list--------------
        ArrayList<Integer> lecids = new ArrayList<>();

        String query = "SELECT l.lecid from course c RIGHT OUTER JOIN lecturer l ON c.lecid=l.lecid WHERE courseid IS NULL";

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

    public int getNumberofEnrollments(){
        return super.getCountOfTable("enrollment");
    }
}
