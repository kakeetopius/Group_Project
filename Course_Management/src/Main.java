import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Main{
    public static void main(String[] args) {
        CourseDAO cdao = new CourseDAO();
        StudentDAO sdao = new StudentDAO();
        LecturerDAO ldao = new LecturerDAO();
        PersonDAO pdao = new PersonDAO();
        EnrollmentDAO edao = new EnrollmentDAO();

        Course course = cdao.getCoursebyName("Database Systems");
        edao.getMarksforCourse(course);
        Map<String, Integer> grades = course.getGrades();
        System.out.println("Marks for course " + course.getCoursename());
        edao.getStudentsinCourse(course);
        ArrayList<Student> students = course.getStudents();
        for (Student student : students) {
            System.out.println(student.getfname() + " " + student.getlname());
        }
    }
}