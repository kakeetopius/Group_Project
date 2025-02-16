import java.util.Scanner;

public class Main{
    public static void main(String[] args) {
        Lecturer lec1 = new Lecturer("Pius", "Kakeeto", "M", "kakeeto@gmail.com", "SCES");
        Scanner sc = new Scanner(System.in);

        StudentDAO sdao = new StudentDAO();
        LecturerDAO ldao = new LecturerDAO();
        EnrollmentDAO enrolldao = new EnrollmentDAO();
        CourseDAO cdao = new CourseDAO();

        Student s1 = (Student) sdao.getPersonByEmail("ejuma@gmail.com");
        enrolldao.getCoursesForStudent(s1);
        Course c1 = cdao.getCoursebyName("Calculus");
        enrolldao.assignMarktoStudent(s1,c1,85);
        s1.displayDetails();

    }
}