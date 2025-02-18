public class Lecturer extends Person {
    private int lecid;
    private Course course;
    private String dept;

    public Lecturer(String fname, String lname, String gender, String email, String dept) {
        super(fname, lname, gender,email);
        this.dept = dept;
    }

    public void setDept(String dept) {this.dept = dept;}
    public String getDept() {return this.dept;}

    public void setLecid(int lecid) { this.lecid = lecid; }
    public int getLecid() { return lecid; }

    public void setCourse(Course course) { this.course = course; }
    public Course getCourse() { return course; }


    @Override
    public void displayDetails() {
        System.out.println("Lecturer ID: " + getLecid());
        System.out.println("Name: " + super.getfname() + " " + super.getlname());
        if (getCourse() != null) {
            System.out.println("Course Teaching: " + course.getCoursename());
        }
        else {
            System.out.println("Course Teaching: None");
        }

    }
}
