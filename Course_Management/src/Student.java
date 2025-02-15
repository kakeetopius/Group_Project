public class Student extends Person{
    private int stdid;

    public Student(String fname, String lname, char gender) {
        super(fname, lname, gender);
    }

    public void setStdid(int stdid) {}
    public int getStdid() {return stdid;}


    @Override
    public void displayDetails() {
        System.out.println("Student ID: " + getStdid());
        System.out.println("Name: " + super.getfname() + " " + super.getlname());

        //display courses enrolled to
    }

}
