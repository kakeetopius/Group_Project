import java.sql.SQLException;
import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        Lecturer lec1 = new Lecturer("Pius", "Kakeeto", "M","kakeeto@gmail.com","SCES");
        Scanner sc = new Scanner(System.in);


        CourseDAO coursedao = new CourseDAO();
        coursedao.deleteCourseByID(5);

    }
}