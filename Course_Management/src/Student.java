import java.util.ArrayList;
import java.util.Map;

public class Student extends Person{
    private int stdid;
    private ArrayList<Course> courses;
    private Map<String, Integer> grades;


    public Student(String fname, String lname, String gender, String email) {
        super(fname, lname, gender, email);
    }

    public void setStdid(int stdid) {this.stdid = stdid;}
    public int getStdid() {return stdid;}

    public void setCourses(ArrayList<Course> courses) {this.courses = courses;}
    public ArrayList<Course> getCourses() {return courses;}

    public void setGrades(Map<String,Integer> grades) {this.grades = grades;}
    public Map<String, Integer> getGrades() {return grades;}

    @Override
    public void displayDetails() {
        System.out.println("Student ID: " + getStdid());
        System.out.println("Name: " + super.getfname() + " " + super.getlname());
        System.out.println("Gender: " + super.getGender());
        System.out.println("Email: " + super.getemail());
        System.out.println("Courses enrolled to: ");


        if(!getCourses().isEmpty()) {
            for (Course course : getCourses()) {
                System.out.println(course.getCoursename());
            }
        }
        else {
            System.out.println("No courses enrolled");
        }
    }

}
