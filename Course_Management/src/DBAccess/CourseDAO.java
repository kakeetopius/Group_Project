package DBAccess;

import BaseClasses.Course;

import java.sql.*;

public class CourseDAO extends DBCon {

    public int addCourse(Course course) {
        String query = String.format("INSERT INTO course(coursename,credit) VALUES('%s',%d)", course.getCoursename(), course.getCredit());

        int status = super.insertData(query);
        if (status == 0) {
            System.out.println("BaseClasses.Course added successfully");
        }
        else {
            System.out.println("Error:BaseClasses.Course not added");
        }
        return status;
    }

    public int getCourseID(String coursename) {
        String query = String.format("SELECT courseid FROM course WHERE coursename = '%s'", coursename);

        try {
            ResultSet rs = super.getData(query);
            if(rs.next()){
                int id = rs.getInt("courseid");
                return id;
            }
        }
        catch (SQLException e) {
            System.out.println("Error getting course id " + e.getMessage());
        }
        return -1;
    }
    public Course getCoursebyId(int courseid) {
        String query = String.format("SELECT * FROM course WHERE courseid=%d", courseid);
        Course course = null;

        try {
            ResultSet rs = super.getData(query);
            if (rs.next()) {
                String coursename = rs.getString("coursename");
                int credit = rs.getInt("credit");
                int cid = rs.getInt("courseid");
                course = new Course(coursename,credit);
            }
            else {
                System.out.println("BaseClasses.Course Not Found");
            }
        }
        catch (SQLException e) {
            System.out.println("Error In getting BaseClasses.Course: " + e.getMessage());
        }
        return course;
    }

    public Course getCoursebyName(String coursename) {
        String query = String.format("SELECT * FROM course WHERE coursename='%s'", coursename);

        Course course = null;
        try {
            ResultSet rs = super.getData(query);
            if (rs.next()) {
                int courseid = rs.getInt("courseid");
                int credit = rs.getInt("credit");
                course = new Course(coursename,credit);
            }
            else {
                System.out.println("BaseClasses.Course Not Found");
            }
        }
        catch (SQLException e) {
            System.out.println("Error In getting BaseClasses.Course: " + e.getMessage());
        }
        return course;
    }

    public int deleteCourseByID(int courseid) {
        String query = String.format("DELETE FROM course WHERE courseid=%d", courseid);

        int status = super.insertData(query);
        if (status == 0) {
            System.out.println("BaseClasses.Course deleted successfully");
        }
        else {
            System.out.println("Error:BaseClasses.Course not deleted");
        }
        return status;
    }
}

