package GUI;

import GUI.Pages.*;

import javax.swing.*;
import java.awt.*;

public class AdminDash extends JPanel{
    private JPanel contentPanel, homePanel;
    private HomePage homePage;
    private InstructorPage instructorPage;
    private CoursePage coursePage;
    private StudentPage studentPage;
    private AnalyticsPage analyticsPage;
    private GradesPage gradesPage;
    private CardLayout cardLayout;


    public AdminDash() {
        setLayout(new BorderLayout());

        homePage = new HomePage();
        instructorPage = new InstructorPage();
        coursePage = new CoursePage();
        studentPage = new StudentPage();
        analyticsPage = new AnalyticsPage();
        gradesPage = new GradesPage();

        // Sidebar
        JPanel sidebar = new JPanel(new GridLayout(7, 1, 0, 5));
        sidebar.setBackground(new Color(50, 50, 50));
        sidebar.setPreferredSize(new Dimension(220, 600));

        // Sidebar Buttons
        JButton homeButton = createSidebarButton("Home");
        JButton coursesButton = createSidebarButton("Manage Courses");
        JButton studentsButton = createSidebarButton("Manage Students");
        JButton instructorsButton = createSidebarButton("Manage Instructors");
        JButton gradesButton = createSidebarButton("Manage Grades");
        JButton analyticsButton = createSidebarButton("View Analytics");


        sidebar.add(homeButton);
        sidebar.add(coursesButton);
        sidebar.add(studentsButton);
        sidebar.add(instructorsButton);
        sidebar.add(gradesButton);
        sidebar.add(analyticsButton);

        add(sidebar, BorderLayout.WEST);

        // Content Panel with CardLayout
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);

        // Adding pages to contentPanel with unique names
        contentPanel.add(homePage, "home");
        contentPanel.add(coursePage, "courses");
        contentPanel.add(studentPage, "students");
        contentPanel.add(instructorPage, "instructors");
        contentPanel.add(analyticsPage, "analytics");
        contentPanel.add(gradesPage, "grades");


        // Sidebar Button Actions
        homeButton.addActionListener(e -> showPage("home"));
        coursesButton.addActionListener(e -> showPage("courses"));
        studentsButton.addActionListener(e -> showPage("students"));
        instructorsButton.addActionListener(e -> showPage("instructors"));
        gradesButton.addActionListener(e -> showPage("grades"));
        analyticsButton.addActionListener(e -> showPage("analytics"));

        add(contentPanel, BorderLayout.CENTER);
    }

    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(70, 130, 180)); // Steel blue
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(200, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void showPage(String pageName) {
        cardLayout.show(contentPanel, pageName); // Show the requested page
    }


}
