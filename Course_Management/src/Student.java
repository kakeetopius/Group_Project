import java.util.ArrayList;

public class Student extends Person{
    private int stdid;
    private ArrayList<Course> courses = new ArrayList<>();

    public Student(String fname, String lname, String gender) {
        super(fname, lname, gender);
    }

    public void setStdid(int stdid) {this.stdid = stdid;}
    public int getStdid() {return stdid;}

    public void addCourse(Course course) {courses.add(course);}
    public ArrayList<Course> getCourses() {return courses;}


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
