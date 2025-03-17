package GUI.Pages;

import BaseClasses.Course;
import BaseClasses.Student;
import DBAccess.CourseDAO;
import DBAccess.StudentDAO;
import GUI.CustomComponents.CustomePanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class DataPages extends CustomePanel {
    //================data components================
    protected LinkedList<Course> allCourses = new LinkedList<>();
    protected ArrayList<Integer> allCourseIDs;
    protected LinkedList<Student> allStudents = new LinkedList<>();
    protected ArrayList<Integer> allStudentIDs;

    //==============gui components====================
    protected JRadioButton studentRadio, courseRadio;
    protected JComboBox<String> selectionBox;
    protected JPanel leftPanel;

    public DataPages(String title) {
        super(title);

        this.setCourses();
        this.setStudents();

        //left panel - Selection Area
        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createTitledBorder("OPTIONS"));
        leftPanel.setPreferredSize(new Dimension(220,getHeight()));

        Font radioFont = new Font("Arial", Font.BOLD, 14);
        studentRadio = new JRadioButton("Student");
        studentRadio.setFont(radioFont);
        studentRadio.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
        courseRadio = new JRadioButton("Course");
        courseRadio.setFont(radioFont);
        courseRadio.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
        ButtonGroup group = new ButtonGroup();
        group.add(studentRadio);
        group.add(courseRadio);

        selectionBox = new JComboBox<>();
        selectionBox.setPreferredSize(new Dimension(200,10));
        selectionBox.setMaximumSize(new Dimension(220,30));
        selectionBox.setMinimumSize(new Dimension(150,30));
        selectionBox.setEnabled(false);

        studentRadio.setAlignmentX(Component.CENTER_ALIGNMENT);
        courseRadio.setAlignmentX(Component.CENTER_ALIGNMENT);
        selectionBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        //adding spacing
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(studentRadio);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(courseRadio);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(selectionBox);

        add(leftPanel, BorderLayout.WEST);

        //Action Listeners
        studentRadio.addActionListener(e -> loadStudentSelection());
        courseRadio.addActionListener(e->loadCourseSelection());
    }

    private void setCourses() {
        //=========gets all course ids from database objects and initializes the allCourses list==============
        CourseDAO cdao = new CourseDAO();
        allCourseIDs = cdao.getAllCourseIDs();

        for (int id : allCourseIDs) {
            allCourses.add(cdao.getCoursebyId(id));
        }
    }

    private void setStudents() {
        StudentDAO sdao = new StudentDAO();
        allStudentIDs = sdao.getAllStudentIDs();

        for (int id : allStudentIDs) {
            allStudents.add(sdao.getStudentByID(id));
        }
    }

    private void loadStudentSelection() {
        selectionBox.setEnabled(true);
        selectionBox.removeAllItems();
        allStudents.clear();
        setStudents();

        for (Student student : allStudents) {
            selectionBox.addItem(student.getname());
        }
    }

    private void loadCourseSelection() {
        selectionBox.setEnabled(true);
        selectionBox.removeAllItems();
        allCourses.clear();
        setCourses();
        for (Course course : allCourses) {
            selectionBox.addItem(course.getCoursename());
        }
    }
}
