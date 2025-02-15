import java.sql.*;

public class StudentDAO extends PersonDAO {

    public int addStudent(Student student){
        String query = "INSERT INTO student(fname,lname,gender,email) VALUES(?,?,?,?)";
        int affectedRows = 0;

        try {
            PreparedStatement stmt = super.con.prepareStatement(query);

            stmt.setString(1,student.getfname());
            stmt.setString(2,student.getlname());
            stmt.setString(3,student.getGender());
            stmt.setString(4,student.getemail());

            affectedRows= stmt.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("Error adding student: " + e.getMessage());
        }

        return affectedRows;
    }

    public Student getStudentByID(int id){
        Student student = null;
        String query = "SELECT * FROM student WHERE stdid = ?";

        try {
            PreparedStatement stmt = super.con.prepareStatement(query);

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            //checking if there is a result
            if(rs.next()) {
                int stdid = rs.getInt(1);
                String fname = rs.getString(2);
                String lname = rs.getString(3);
                String email = rs.getString(4);
                String gender = rs.getString(8);
                String password = rs.getString(9);

                student = new Student(fname,lname,gender,email);
                student.setStdid(stdid);
                student.setPassword(password);
            }
            else {
                System.out.println("Student not found");
            }
        }
        catch (SQLException e) {
            System.out.println("Error Getting Student: " + e.getMessage());
        }

        return student;
    }


    public int deleteStudentById(int studid)  {
        String query = "DELETE FROM student WHERE stdid = ?";
        try{
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1,studid);
            int affected_rows = stmt.executeUpdate();
            if (affected_rows > 0){
                System.out.println("Student successfully deleted");
            }
            else {
                System.out.println("Student not found. Could not delete");
            }
            return affected_rows;
        }
        catch (SQLException e){
            System.out.println("Error deleting student");
            return -1;
        }
    }
}



