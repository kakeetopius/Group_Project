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
    public int addStudenttoCourse(Student student, Course course) {
        String coursename = course.getCoursename();
        int stdid = student.getStdid();
        int courseid = course.getCourseid();
        String query = String.format("INSERT INTO enrollment(courseid, stdid)VALUES(%d, %d)", courseid, stdid);

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
    public int assignLectoCourse(Lecturer lec, Course course) {
        int lecid = lec.getLecid();
        int courseid = course.getCourseid();

        String query = String.format("UPDATE enrollment SET lecid = %d, courseid = %d", lecid, courseid);

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
    public void getCoursesForStudent(Student student) {
        ArrayList<Course> courses = new ArrayList<>();
        int stdid = student.getStdid();
        String query = String.format("SELECT courseid FROM course NATURAL JOIN enrollment WHERE stdid = %d", stdid);
        try{
            ResultSet rs = super.getData(query);
            while(rs.next()) {
                int courseid = rs.getInt("courseid");
                courses.add(courseDAO.getCoursebyId(courseid));
            }
        }
        catch (SQLException e) {
            System.out.println("Error setting courses for student :" + e.getMessage());
        }
        student.setCourses(courses);
    }

    public void getCourseForLecture(Lecturer lec) {
        int lecid = lec.getLecid();
        String query = String.format("SELECT courseid FROM course WHERE lecid = %d", lecid);
        try {
            ResultSet rs = super.getData(query);
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

        String query = String.format("SELECT stdid FROM enrollment WHERE courseid = %d", courseid);

        try {
            ResultSet rs = super.getData(query);
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
        String query = String.format("UPDATE enrollment SET grade = %d WHERE courseid = %d AND stdid = %d", marktoassign, courseid, stdid);

        int status = super.insertData(query);
        if (status == 0) {
            System.out.println("Mark successfully assigned to student");
        }
        else {
            System.out.println("Mark could not be assigned to student");
        }
        return status;
    }


    public void getMarksforStudent(Student student) {
        Map<String, Integer> marks = new HashMap<>();

        int stdid = student.getStdid();

        String query = String.format("SELECT coursename, grade FROM enrollment NATURAL JOIN course WHERE stdid=%d",stdid);
        try {
            ResultSet rs = super.getData(query);
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
        String query = String.format("SELECT fname, lname, grade FROM enrollment NATURAL JOIN student WHERE courseid = %d",courseid);

        try {
            ResultSet rs = super.getData(query);
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
