import java.util.*;

public class Course {
    private int courseid;
    private String coursename;
    private int credit;
    private Lecturer lecturer;
    private ArrayList<Student> students;
    private Map<String, Integer> grades;


    public Course(String coursename, int credit) {
        this.coursename = coursename;
        this.credit = credit;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    public int getCourseid() {
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

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setGrades(Map<String, Integer> grades) {
        this.grades = grades;
    }

    public Map<String, Integer> getGrades() {
        return grades;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void displayDetails() {
        System.out.println("Course Name: " + coursename);
        System.out.println("Credit: " + credit);
        if (this.lecturer != null) {
            System.out.println("Lecturer: " + lecturer.getfname() + " " + lecturer.getlname());
        } else {
            System.out.println("Lecturer: Not assigned");
        }
        System.out.println("Students Enrolled : ");
        if (students != null) {
            for (Student student : students) {
                System.out.println(student.getfname() + " " + student.getlname());
            }
        } else {
            System.out.println("None");
        }
    }
}
