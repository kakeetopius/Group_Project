package GUI;

import javax.swing.*;
import java.awt.*;

public class AdminDash extends JPanel{
    private JPanel contentPanel, homePanel;

    public AdminDash() {
        setLayout(new BorderLayout());

        // Sidebar
        JPanel sidebar = new JPanel(new GridLayout(8, 1, 0, 5));
        sidebar.setBackground(new Color(50,50,50));
        sidebar.setPreferredSize(new Dimension(220, 600));

        // Sidebar Buttons
        JButton homeButton = createSidebarButton("Home");
        JButton coursesButton = createSidebarButton("Manage Courses");
        JButton studentsButton = createSidebarButton("Manage Students");
        JButton instructorsButton = createSidebarButton("Manage Instructors");
        JButton gradesButton = createSidebarButton("Manage Grades");
        JButton analyticsButton = createSidebarButton("View Analytics");
        JButton settingsButton = createSidebarButton("Settings");

        sidebar.add(homeButton);
        sidebar.add(coursesButton);
        sidebar.add(studentsButton);
        sidebar.add(instructorsButton);
        sidebar.add(gradesButton);
        sidebar.add(analyticsButton);
        sidebar.add(settingsButton);

        add(sidebar, BorderLayout.WEST);

        // Content Panel with CardLayout
        contentPanel = new JPanel(new CardLayout());
        CardLayout cardLayout1 = (CardLayout) contentPanel.getLayout(); //for the content panel(inside admin dashboard)


        // Home Panel
        homePanel = new JPanel(new BorderLayout());
        contentPanel.add(new HomePage(), "homeContent");

        // Other Sections
        contentPanel.add(new CoursePage(), "coursesContent");
        contentPanel.add(createPanel("Students Panel"), "studentsContent");
        contentPanel.add(createPanel("Grades Panel"), "gradesContent");
        contentPanel.add(createPanel("Analytics Panel"), "analyticsContent");
        contentPanel.add(createPanel("Settings Panel"), "settingsContent");

        // Sidebar Button Actions
        homeButton.addActionListener(e -> cardLayout1.show(contentPanel, "homeContent"));
        coursesButton.addActionListener(e -> cardLayout1.show(contentPanel, "coursesContent"));
        studentsButton.addActionListener(e -> cardLayout1.show(contentPanel, "studentsContent"));
        gradesButton.addActionListener(e -> cardLayout1.show(contentPanel, "gradesContent"));
        analyticsButton.addActionListener(e -> cardLayout1.show(contentPanel, "analyticsContent"));
        settingsButton.addActionListener(e -> cardLayout1.show(contentPanel, "settingsContent"));

        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createPanel(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 255, 255));
        JLabel label = new JLabel(title, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(70, 130, 180)); // Steel blue
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(200, 40));
        return button;
    }
}
