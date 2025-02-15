public class Lecturer extends Person {
    private int lecid;
    private int courseid = 0;

    public Lecturer(String fname, String lname, char gender) {
        super(fname, lname, gender);
    }


    public void setLecid(int lecid) { this.lecid = lecid; }
    public int getLecid() { return lecid; }

    public void setCourseid(int courseid) { this.courseid = courseid; }
    public int getCourseid() { return courseid; }


    @Override
    public void displayDetails() {
        System.out.println("Lecturer ID: " + getLecid());
        System.out.println("Name: " + super.getfname() + " " + super.getlname());
        if (this.courseid == 0) {
            System.out.println("Course taught: None");
        }
        else {
            //get course name by id and display
        }
    }
}
