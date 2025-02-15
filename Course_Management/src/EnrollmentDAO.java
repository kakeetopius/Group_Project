/* to deal with all logic
concerning enrolling students
to courses and getting statistics
about enrollments from database
 */

import java.sql.*;
import java.util.ArrayList;

public class EnrollmentDAO extends DBCon{
    Connection con = super.getConnection();

    public int addStudenttoCourseByIds(int stdid, int courseid) {

    }

    public int assignLectoCourseByIds(int lecid, int courseid) {

    }

    public void setCoursesForStudent(int stdid) {

    }

    public void setCoursesForLecture(int lecid) {

    }

    public ArrayList<Integer> getMarksforStudent(int stdid) {

    }

    public ArrayList<Integer> getMarksforCourse(String coursename) {

    }

    public boolean checkifCourseExists(String coursename) {

    }

    public boolean checkifStudentExists(int stdid) {

    }

    public boolean checkifLectureExists(int lecid) {

    }


}
