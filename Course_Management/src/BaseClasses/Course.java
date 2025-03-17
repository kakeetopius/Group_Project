package BaseClasses;
import DBAccess.*;

import java.util.*;

public class Course implements Statistics {
    private int courseid = -1;  //---------initially -1 to signal to the setter function to set id from database.
    private String coursename;
    private int credit;
    private Lecturer lecturer;
    private ArrayList<Student> students; //-------list of all students in the course.
    private Map<Integer, Integer> grades; //-------to store enrolled students ids and corresponding grades


    public Course(String coursename, int credit) {
        this.coursename = coursename;
        this.credit = credit;
    }

    private void setCourseIDfromDB() {
        CourseDAO cdao = new CourseDAO();
        this.courseid = cdao.getCourseID(this.coursename);
    }

    public int getCourseid() {
        if (courseid == -1) {
            setCourseIDfromDB();
        }
        return this.courseid;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getCredit() {
        return credit;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    private void setLecturerfromDB() {
        EnrollmentDAO edao = new EnrollmentDAO();
        this.lecturer = edao.getLecforCourse(getCourseid());
    }

    public Lecturer getLecturer() {
        setLecturerfromDB();
        return lecturer;
    }

    private void setCourseGradesFromDB() {
        EnrollmentDAO edao = new EnrollmentDAO();
        grades = edao.getMarksforCourse(getCourseid());
    }

    public Map<Integer, Integer> getGrades() {
        setCourseGradesFromDB();
        return grades;
    }

    private void setStudentsfromDB() {
        EnrollmentDAO edao = new EnrollmentDAO();
        this.students = edao.getStudentsinCourse(getCourseid());
    }

    public ArrayList<Student> getStudents() {
        setStudentsfromDB();
        return students;
    }

    public int getStudentsCount() {
        setStudentsfromDB();
        if (students.isEmpty()) {
            return 0;
        }
        return students.size();
    }

    @Override
    public double calculateAverageGrade() {
        int total_grades = calculateTotalGrades();
        int number_of_students = getStudentsCount();
        return (double) total_grades / number_of_students;
    }

    @Override
    public int calculateTotalGrades() {
        setCourseGradesFromDB();
        int total = 0;
        for (int mark : grades.values()) {
            total += mark;
        }
        return total;
    }
}
