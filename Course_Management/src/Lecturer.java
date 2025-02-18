public class Lecturer extends Person {
    private int lecid = -1;
    private Course course;
    private String dept;

    public Lecturer(String fname, String lname, String gender, String email, String dept) {
        super(fname, lname, gender,email);
        this.dept = dept;
    }

    public void setDept(String dept) {this.dept = dept;}
    public String getDept() {return this.dept;}

    private void setLecidFromDB() {
        LecturerDAO ldao = new LecturerDAO();
        this.lecid = ldao.getLecturerID(super.getemail());
    }

    public int getLecID() {
        if (lecid == -1) {
            setLecidFromDB();
        }
        return this.lecid;
    }

    private void setCourseFromDB() {
        EnrollmentDAO edao = new EnrollmentDAO();
        this.course = edao.getCourseForLecturer(this.getLecID());
    }

    public Course getCourse() {
        setCourseFromDB();
        return this.course;
    }


    @Override
    public void displayDetails() {
        System.out.println("Lecturer ID: " + getLecID());
        System.out.println("Name: " + super.getfname() + " " + super.getlname());
        if (getCourse() != null) {
            System.out.println("Course Teaching: " + course.getCoursename());
        }
        else {
            System.out.println("Course Teaching: None");
        }

    }
}
