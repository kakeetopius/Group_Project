package BaseClasses;
import DBAccess.*;

import java.util.ArrayList;
import java.util.Map;

public class Student extends Person implements Statistics{
    private int stdid = -1; //----initially -1 to signal to the setter method to set the id from the database.
    private ArrayList<Course> courses;  //-------to store courses the student is enrolled in.
    private Map<String, Integer> grades; //---------to store the student grades and the corresponding coursename.


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
        System.out.println("Student ID: " + getStdid());
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

    @Override
    public double calculateAverageGrade() {
        int total = calculateTotalGrades();
        int number_of_courses = getNumberofCourses();
        return (double) total /number_of_courses;
    }

    @Override
    public int calculateTotalGrades() {
        setGradesfromDB();
        int total = 0;
        for (int mark : grades.values()) {
            total += mark;
        }
        return total;
    }


    public int getNumberofCourses() {
        setGradesfromDB();
        return courses.size();
    }
}
