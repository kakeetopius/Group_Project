import java.sql.*;

public class StudentDAO extends DBCon {
    private Student student;
    Connection con = super.getConnection();


    public int addStudent(Student student) throws SQLException {
        String query = "INSERT INTO student(fname,lname,gender,email) VALUES(?,?,?,?)";
        PreparedStatement stmt = con.prepareStatement(query);

        stmt.setString(1,student.getfname());
        stmt.setString(2,student.getlname());
        stmt.setString(3,student.getGender());
        stmt.setString(4,student.getemail());

        int affectedRows = stmt.executeUpdate();
        return affectedRows;
    }

    public Student getStudentByID(int id) throws SQLException {
        Student student = null;
        String query = "SELECT * FROM student WHERE stdid = ?";
        PreparedStatement stmt = con.prepareStatement(query);

        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        //checking if there is a result
        if(rs.next()) {
            int stdid = rs.getInt(1);
            String fname = rs.getString(2);
            String lname = rs.getString(3);
            String email = rs.getString(4);
            String gender = rs.getString(8);

            student = new Student(fname,lname,gender);
            student.setemail(email);
            student.setStdid(stdid);

        }
        else {
            System.out.println("Student not found");
        }
        return student;
    }


}

