public abstract class Person {
    private String fname;
    private String lname;
    private String email;
    private String phone;
    private String dob;
    private char gender;

    public Person(String fname, String lname, char gender) {
        this.fname = fname;
        this.lname = lname;
        this.gender = gender;
    }

    public void setfname(String fname) {this.fname = fname;}
    public String getfname() {return this.fname;}

    public void setlname(String lname) {this.lname = lname;}
    public String getlname() {return this.lname;}

    public void setGender(char gender) {this.gender = gender;}
    public char getGender() {return this.gender;}

    public void setemail(String email) {this.email = email;}
    public String getemail() {return this.email;}

    public void setphone(String phone) {this.phone = phone;}
    public String getphone() {return this.phone;}

    public void setdob(String dob) {this.dob = dob;}
    public String getdob() {return this.dob;}

    //abstract method to display details
    public abstract void displayDetails();
}
