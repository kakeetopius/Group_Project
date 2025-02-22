
public class Main{
    public static void main(String[] args) {
        Student s1 = (Student) new PersonDAO().getPersonByEmail("ejuma@gmail.com");
        s1.displayDetails();
    }
}