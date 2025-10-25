package GUI.Pages;

import DBAccess.CourseDAO;
import DBAccess.EnrollmentDAO;
import DBAccess.LecturerDAO;
import DBAccess.StudentDAO;
import GUI.CustomComponents.CustomePanel;

import javax.swing.*;
import java.awt.*;

public class HomePage extends CustomePanel {
    String totalstudents,totalcourses,totalinstructors,totalenrollments;
    JPanel statsPanel;


    public HomePage() {
        super("Welcome Admin!");
        loadData();
        initializeUI();
    }

    // Helper method to create statistic panels
    private JPanel createStatCard(String title, String value) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setForeground(Color.DARK_GRAY);

        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 20));
        valueLabel.setForeground(new Color(70, 130, 180));

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(valueLabel, BorderLayout.CENTER);
        return panel;
    }

    private void loadData() {
        totalstudents = String.valueOf(new StudentDAO().getNumberOfStudents());
        totalcourses = String.valueOf(new CourseDAO().getNumberOfCourses());
        totalinstructors = String.valueOf(new LecturerDAO().getNumberOfLecturers());
        totalenrollments = String.valueOf(new EnrollmentDAO().getNumberofEnrollments());
    }

    // Initialize UI components
    private void initializeUI() {
        // ===== Stats Panel =====
        statsPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        statsPanel.add(createStatCard("Total Students", totalstudents));
        statsPanel.add(createStatCard("Total Courses", totalcourses));
        statsPanel.add(createStatCard("Total Instructors", totalinstructors));
        statsPanel.add(createStatCard("Active Enrollments", totalenrollments));

        add(statsPanel, BorderLayout.CENTER);
    }


}
