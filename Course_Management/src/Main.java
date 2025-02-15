import java.sql.SQLException;

public class Main{
    public static void main(String[] args) throws SQLException {
        //Student stud1 = new Student("Pius", "Kakeeto", "M");
        //stud1.setemail("kakeeto@gmail.com");

        StudentDAO stdao = new StudentDAO();
        Student student = stdao.getStudentByID(100);
        if(student != null) {
            student.displayDetails();
        }


    }
}