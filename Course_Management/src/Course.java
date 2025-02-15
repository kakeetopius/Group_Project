public class Course {
    private int courseid;
    private String coursename;
    private int credit;
    private int lecid = 0;

    public Course(String coursename, int credit) {
        this.coursename = coursename;
        this.credit = credit;
    }

    public void setCourseid(int courseid) {this.courseid = courseid;}
    public int getCourseid() {return courseid;}

    public void setCredit(int credit) {this.credit = credit;}
    public int getCredit() {return credit;}

    public void setLecid(int lecid) {this.lecid = lecid;}
    public int getLecid() {return lecid;}

    public String getCoursename() {return coursename;}
    public void setCoursename(String coursename) {this.coursename = coursename;}

}
