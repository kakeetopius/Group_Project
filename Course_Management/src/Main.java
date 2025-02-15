import java.sql.SQLException;
import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        Lecturer lec1 = new Lecturer("Pius", "Kakeeto", "M","kakeeto@gmail.com","SCES");
        Scanner sc = new Scanner(System.in);

        LecturerDAO lecDAO = new LecturerDAO();
        lec1 = lecDAO.getLecturerByID(4);
        lec1.displayDetails();


        /*System.out.println("Enter email: ");
        String email = sc.nextLine();
        PersonDAO stddao = new PersonDAO();
        Person person = stddao.getPersonByEmail(email);

        if(person instanceof Student){
            System.out.println("Login for Student");
            person.displayDetails();
        }
        else if (person instanceof Lecturer) {
            System.out.println("Login for Lecturer");
            person.displayDetails();
        }
        System.out.println("Enter New Password: ");
        String pass = sc.nextLine();
        stddao.updatePassword(person, pass);*/
    }
}