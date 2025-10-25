package DBAccess;

import BaseClasses.Course;

import java.sql.*;
import java.util.ArrayList;

public class CourseDAO extends DBCon {
    private Course course = null;

    public int addCourse(Course course) {
        //-----The fuction adds a course to the database using a course object-------
        String query = String.format("INSERT INTO course(coursename,credit) VALUES('%s',%d)", course.getCoursename(), course.getCredit());

        int status = super.insertData(query);
        if (status == 0) {
            System.out.println("Course added successfully");
        } else {
            System.out.println("Course not added");
        }
        return status;
    }

    public ArrayList<Integer> getAllCourseIDs() {
        //----List to store all the ids for the courses in the database------------------
        ArrayList<Integer> courseids = new ArrayList<>();

        String query = "SELECT courseid FROM course ORDER BY(courseid)";
        int id;

        try {
            ResultSet rs = super.getData(query);
            while (rs.next()) {
                id = rs.getInt("courseid");
                courseids.add(id);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return courseids;
    }

    public int getCourseID(String coursename) {
        String query = String.format("SELECT courseid FROM course WHERE coursename = '%s'", coursename);

        try {
            ResultSet rs = super.getData(query);
            if (rs.next()) {
                int id = rs.getInt("courseid");
                return id;
            }
        } catch (SQLException e) {
            System.out.println("Error getting course id " + e.getMessage());
        }
        return -1;
    }

    public Course getCoursebyId(int courseid) {
        //----The function returns a course object when given the courseid-------------
        String query = String.format("SELECT * FROM course WHERE courseid=%d", courseid);

        try {
            ResultSet rs = super.getData(query);
            if (rs.next()) {
                String coursename = rs.getString("coursename");
                int credit = rs.getInt("credit");
                int cid = rs.getInt("courseid");
                course = new Course(coursename, credit);
            } else {
                System.out.println("BaseClasses.Course Not Found");
            }
        } catch (SQLException e) {
            System.out.println("Error In getting BaseClasses.Course: " + e.getMessage());
        }
        return course;
    }

    public Course getCoursebyName(String coursename) {
        //-----function returns course object when given the course name----------------
        String query = String.format("SELECT * FROM course WHERE coursename='%s'", coursename);

        try {
            ResultSet rs = super.getData(query);
            if (rs.next()) {
                int courseid = rs.getInt("courseid");
                int credit = rs.getInt("credit");
                course = new Course(coursename, credit);
            } else {
                System.out.println("Course Not Found");
            }
        } catch (SQLException e) {
            System.out.println("Error In getting Course: " + e.getMessage());
        }
        return course;
    }

    public int deleteCourseByID(int courseid) {
        String query = String.format("DELETE FROM course WHERE courseid=%d", courseid);

        int status = super.insertData(query);
        if (status == 0) {
            System.out.println("Course deleted successfully");
        } else {
            System.out.println("Error:Course not deleted");
        }
        return status;
    }

    public int editCourseField(String fieldname, String value, int courseid) {
        String query;

        if (fieldname.equals("coursename")) {
            query = String.format("UPDATE course SET coursename = '%s' WHERE courseid = %d", value, courseid);
        }
        else if (fieldname.equals("credit")) {
            query = String.format("UPDATE course SET credit = %d WHERE courseid =%d", Integer.parseInt(value), courseid);
        }
        else if (fieldname.equals("courseid")) {
            System.out.println("Cannot edit courseid");
            return -1;
        }
        else {
            System.out.println("Invalid fieldname");
            return -1;
        }

        try {
            int status = super.insertData(query);
            if (status == 0) {
                System.out.println("Course updated successfully");
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        }
        return 0;
    }

    public int getNumberOfCourses() {
        return super.getCountOfTable("course");
    }
}

