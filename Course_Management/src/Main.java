import java.sql.SQLException;
import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        Lecturer lec1 = new Lecturer("Pius", "Kakeeto", "M","kakeeto@gmail.com","SCES");
        Scanner sc = new Scanner(System.in);

        StudentDAO sdao = new StudentDAO();
        LecturerDAO ldao = new LecturerDAO();
        EnrollmentDAO enrolldao = new EnrollmentDAO();
        if(enrolldao.checkifCourseExists("Maisha")) {
            System.out.println("Maisha course already exists");
        }
        else {
            System.out.println("Course does not exist");
        }

        if(enrolldao.checkifLectureExists(1)) {
            System.out.println("Lecture exists");
            System.out.println("Name: " + ldao.getLecturerByID(1).getfname());
        }
        else {
            System.out.println("Lecture does not exist");
        }

        if(enrolldao.checkifStudentExists(2)) {
            System.out.println("Student exists");
            System.out.println("Name: " + sdao.getStudentByID(2).getfname());
        }
        else {
            System.out.println("Student does not exist");
        }

    }
}