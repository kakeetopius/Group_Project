import java.sql.*;

public class LecturerDAO extends PersonDAO {

    public int addLecturer(Lecturer lec){
        String query = "INSERT INTO lecturer(fname,lname,gender,email,dept) VALUES(?,?,?,?,?)";
        int affectedRows = 0;

        try {
            PreparedStatement stmt = super.con.prepareStatement(query);

            stmt.setString(1,lec.getfname());
            stmt.setString(2,lec.getlname());
            stmt.setString(3,lec.getGender());
            stmt.setString(4,lec.getemail());
            stmt.setString(5,lec.getDept());

            affectedRows= stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Successfully added lecturer: " + lec.getfname());
            }
        }
        catch(SQLException e){
            System.out.println("Error adding Lecturer: " + e.getMessage());
        }

        return affectedRows;
    }

    public Lecturer getLecturerByID(int id){
        Lecturer lec = null;
        String query = "SELECT * FROM lecturer WHERE lecid = ?";

        try {
            PreparedStatement stmt = super.con.prepareStatement(query);

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            //checking if there is a result
            if(rs.next()) {
                int lecid = rs.getInt(1);
                String fname = rs.getString(2);
                String lname = rs.getString(3);
                String email = rs.getString(4);
                String dept = rs.getString(7);
                String gender = rs.getString(8);
                String password = rs.getString(9);

                lec = new Lecturer(fname,lname,gender,email,dept);
                lec.setLecid(lecid);
                lec.setPassword(password);
            }
            else {
                System.out.println("Lecturer not found");
            }
        }
        catch (SQLException e) {
            System.out.println("Error Getting Lecturer: " + e.getMessage());
        }
        return lec;
    }


    public int deleteLecturerById(int lecid)  {
        String query = "DELETE FROM lecturer WHERE lecid = ?";
        try{
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1,lecid);
            int affected_rows = stmt.executeUpdate();
            if (affected_rows > 0){
                System.out.println("Lecturer successfully deleted");
            }
            else {
                System.out.println("Lecturer not found. Could not delete");
            }
            return affected_rows;
        }
        catch (SQLException e){
            System.out.println("Error deleting Lecturer");
            return -1;
        }
    }
}



