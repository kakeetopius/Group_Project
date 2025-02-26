package BaseClasses;
import DBAccess.*;

import java.util.ArrayList;
import java.util.Map;

public class Student extends Person {
    private int stdid = -1;
    private ArrayList<Course> courses;
    private Map<String, Integer> grades;


    public Student(String fname, String lname, String gender, String email) {
        super(fname, lname, gender, email);
    }

    private void setStdidFromDB() {
        StudentDAO sdao = new StudentDAO();
        this.stdid = sdao.getStudentID(super.getemail());
    }

    public int getStdid() {
        if (stdid == -1) {
            setStdidFromDB();
        }
        return stdid;
    }

    private void setCoursesfromDB() {
        EnrollmentDAO edao = new EnrollmentDAO();
        this.courses = edao.getCoursesForStudent(this.getStdid());
    }

    public ArrayList<Course> getCourses() {
        setCoursesfromDB();
        return courses;
    }

    private void setGradesfromDB() {
        EnrollmentDAO edao = new EnrollmentDAO();
        this.grades = edao.getMarksforStudent(this.getStdid());
    }

    public Map<String, Integer> getGrades() {
        setGradesfromDB();
        return grades;
    }

    @Override
    public void displayDetails() {
        System.out.println("BaseClasses.Student ID: " + getStdid());
        System.out.println("Name: " + super.getfname() + " " + super.getlname());
        System.out.println("Gender: " + super.getGender());
        System.out.println("Email: " + super.getemail());
        System.out.println("Courses enrolled to: ");


        if(getCourses() != null) {
            for (Course course : getCourses()) {
                System.out.println(course.getCoursename());
            }
        }
        else {
            System.out.println("No courses enrolled");
        }
    }

    public void displayMarks() {
        if (getGrades() != null) {
            System.out.println("Marks for: " + getfname() + " " + getlname());
            System.out.println("BaseClasses.Student ID: " + getStdid());
            for (String coursename : grades.keySet()) {
                System.out.println(coursename + ":" + grades.get(coursename));
            }
        }
        else {
            System.out.println("No Marks to display");
        }
    }
}
