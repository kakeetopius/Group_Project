import java.util.*;

public class Course {
    private int courseid = -1;
    private String coursename;
    private int credit;
    private Lecturer lecturer;
    private ArrayList<Student> students;
    private Map<String, Integer> grades;


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
        this.lecturer = edao.getLecforCourse(this.courseid);
    }

    public Lecturer getLecturer() {
        setLecturerfromDB();
        return lecturer;
    }

    private void setCourseGradesFromDB() {
        EnrollmentDAO edao = new EnrollmentDAO();
        grades = edao.getMarksforCourse(courseid);
    }

    public Map<String, Integer> getGrades() {
        setCourseGradesFromDB();
        return grades;
    }

    private void setStudentsfromDB() {
        EnrollmentDAO edao = new EnrollmentDAO();
        this.students = edao.getStudentsinCourse(courseid);
    }

    public ArrayList<Student> getStudents() {
        setStudentsfromDB();
        return students;
    }

    public void displayDetails() {
        System.out.println("Course Name: " + this.coursename);
        System.out.println("Course Number: " + this.getCourseid());
        System.out.println("Credit: " + this.credit);
        if (this.getLecturer() != null) {
            System.out.println("Lecturer: " + lecturer.getfname() + " " + lecturer.getlname());
        } else {
            System.out.println("Lecturer: Not assigned");
        }
        System.out.println("Students Enrolled : ");
        if (this.getStudents() != null) {
            for (Student student : students) {
                System.out.println(student.getfname() + " " + student.getlname());
            }
        } else {
            System.out.println("None");
        }
    }
}
