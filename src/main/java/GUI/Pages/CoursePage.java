package GUI.Pages;

import BaseClasses.Course;
import BaseClasses.Lecturer;
import DBAccess.CourseDAO;
import DBAccess.EnrollmentDAO;
import DBAccess.LecturerDAO;
import GUI.CustomComponents.CustomTable;
import GUI.CustomComponents.CustomePanel;
import GUI.GuiUtils.GUIHelper;
import GUI.GuiUtils.InfoPopUP;
import GUI.GuiUtils.InputPopUP;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.LinkedList;

public class CoursePage extends CustomePanel {
    private DefaultTableModel tableModel;
    private CustomTable courseTable;
    private JButton viewButton, addButton, editButton, deleteButton;
    private ArrayList<Course> courses = new ArrayList<>();
    private LinkedList<Lecturer> lecturers = null;
    private JComboBox<String> leccomboBox = null;

    public CoursePage() {
        super("Course Management Page");

        //--------Course Table--------------
        String[] columnnames = {"CourseID", "Course Name", "Instructor", "Credits"};

        tableModel = new DefaultTableModel(columnnames, 0);
        courseTable = new CustomTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(courseTable);

        add(scrollPane, BorderLayout.CENTER);
        addAllCoursestoTable();

        //--------Button Panel------------------
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        viewButton = new JButton("View Details");
        addButton = new JButton("Add Course");
        editButton = new JButton("Edit Course");
        deleteButton = new JButton("Delete Course");

        buttonPanel.add(viewButton);
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(buttonPanel, BorderLayout.SOUTH);


        //----------Event Listeners----------------
        viewButton.addActionListener(e -> viewCourseDetails());
        addButton.addActionListener(e -> addCourse());
        editButton.addActionListener(e -> editCourse());
        deleteButton.addActionListener(e -> deleteCourse());
    }

    private void viewCourseDetails() {
        int selectedRow = courseTable.getSelectedRow();

        //positioning
        GridBagConstraints gbc = GUIHelper.getGBC();

        String coursename = (String) tableModel.getValueAt(selectedRow, 1);
        String instructorname = (String) tableModel.getValueAt(selectedRow, 2);

        Course c = new CourseDAO().getCoursebyName(coursename);

        JLabel namelabel = new JLabel("COURSE NAME: " + c.getCoursename());

        JLabel instructor = new JLabel("INSTRUCTOR: " + instructorname);
        JLabel studentNo = new JLabel("NUMBER OF STUDENTS: " + c.getStudentsCount());

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a course to view", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            LinkedList<JComponent> components = new LinkedList<>();
            components.add(namelabel);
            components.add(instructor);
            components.add(studentNo);
            InfoPopUP p = GUIHelper.createInfoPopUP("Course Details", gbc, components);
            p.setVisible(true);
        }
    }

    private void addCourse() {
        JTextField coursnameField, creditsField;
        InputPopUP p = GUIHelper.createInputPopUP("Add New Course");

        //positioning
        GridBagConstraints gbc = GUIHelper.getGBC();

        coursnameField = new JTextField();
        creditsField = new JTextField();

        initializeLecComboBox();
        p.addField("Course Name: ", coursnameField, gbc);
        p.addField("Available Instructors : ", leccomboBox, gbc);
        p.addField("Credits: ", creditsField, gbc);

        p.setVisible(true);


        if (p.isConfirmed()) {
            if (!coursnameField.getText().isEmpty() && !creditsField.getText().isEmpty()) {
                String coursename = coursnameField.getText();
                String credits = creditsField.getText();
                int selectedLecIndex = leccomboBox.getSelectedIndex();

                CourseDAO cdao = new CourseDAO();
                Course course = new Course(coursename, Integer.parseInt(credits));
                cdao.addCourse(course);
                Course new_course = cdao.getCoursebyName(coursename);// get object
                if (selectedLecIndex != -1) { //if a lecturer is selected
                    int lecid = lecturers.get(selectedLecIndex).getLecID();
                    EnrollmentDAO edao = new EnrollmentDAO();
                    edao.assignLectoCourse(lecid, new_course.getCourseid());
                    addtoTable(String.valueOf(new_course.getCourseid()), coursename, new_course.getLecturer().getname(), String.valueOf(new_course.getCredit()));
                } else {
                    addtoTable(String.valueOf(new_course.getCourseid()), coursename, "None", String.valueOf(new_course.getCredit()));
                }
            } else {
                JOptionPane.showMessageDialog(this, "Incomplete form", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void initializeLecComboBox() {
        //JCombo to choose a lecturer.
        getInstructorChoices();
        String[] lecnames = new String[lecturers.size()];
        for (int i = 0; i < lecturers.size(); i++) {
            lecnames[i] = lecturers.get(i).getname();
        }
        this.leccomboBox = new JComboBox<>(lecnames);
    }

    private void editCourse() {
        int selectedRow = courseTable.getSelectedRow();
        int selectedCol = courseTable.getSelectedColumn();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select Course to edit");
            return;
        }

        InputPopUP p = GUIHelper.createInputPopUP("Edit Course");

        int courseid = Integer.parseInt(courseTable.getValueAt(selectedRow, 0).toString().split("C")[1]);
        String colname = (String) courseTable.getValueAt(selectedRow, selectedCol);
        System.out.println("User selected this cell: " + colname);

        GridBagConstraints gbc = GUIHelper.getGBC();

        //finding which column to edit.

        if (selectedCol == 0) {
            JOptionPane.showMessageDialog(this, "Cannot change the course id");
            return;
        }
        else if (selectedCol == 1) {
            JTextField newcourse = new JTextField();
            p.addField("New Course Name: ", newcourse, gbc);
            p.setVisible(true);
            String newcoursename = newcourse.getText();
            if (p.isConfirmed() && !newcoursename.isEmpty()) {
                if (this.editCourseName(newcoursename, courseid) == 0) {
                    JOptionPane.showMessageDialog(this, "Course Name Updated Successfully");
                    courseTable.setValueAt(newcoursename, selectedRow, selectedCol);
                } else {
                    JOptionPane.showMessageDialog(this, "Erro updating Course Name");
                }
            }
        }
        else if (selectedCol == 2) {
            this.initializeLecComboBox();
            p.addField("New Lecturer: ", leccomboBox, gbc);
            p.setVisible(true);
            int selectedLecIndex = leccomboBox.getSelectedIndex();
            if (selectedLecIndex == -1) {
                JOptionPane.showMessageDialog(this, "Select Lecturer to edit");
            }
            else {
                if (p.isConfirmed()) {
                    int lecid = lecturers.get(selectedLecIndex).getLecID();
                    if (this.editCourseLec(lecid, courseid) == 0) {
                        JOptionPane.showMessageDialog(this, "Lecturer Edited Successfully");
                        courseTable.setValueAt(lecturers.get(selectedLecIndex).getname(), selectedRow, selectedCol);
                    } else {
                        JOptionPane.showMessageDialog(this, "Error updating Course Instructor");
                    }
                }
            }
        } else if (selectedCol == 3) {
            JTextField credits = new JTextField();
            p.addField("New Course Credits: ", credits, gbc);
            p.setVisible(true);
            String newcredits = credits.getText();
            if (p.isConfirmed() && !newcredits.isEmpty()) {
                if (this.editCourseCredits(newcredits, courseid) == 0) {
                    JOptionPane.showMessageDialog(this, "Course Credits Updated Successfully");
                    courseTable.setValueAt(newcredits, selectedRow, selectedCol);
                } else {
                    JOptionPane.showMessageDialog(this, "Error updating Course Credits");
                }
            }
        }
    }

    private void deleteCourse() {
        int selectedRow = courseTable.getSelectedRow();
        CourseDAO cdao = new CourseDAO();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a course to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this course?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String courseid = (String) tableModel.getValueAt(selectedRow, 0);
            courseid = courseid.split("C")[1];
            System.out.println("Deleting course with id " + courseid);
            int cid = Integer.parseInt(courseid);
            if (cdao.deleteCourseByID(cid) == 0) {
                JOptionPane.showMessageDialog(this, "Course Deleted Successfully");
                tableModel.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Course Deletion Failed");
            }
        }
    }

    private void addtoTable(String courseID, String courseName, String instructor, String credits) {
        Object[] row = {courseID, courseName, instructor, credits};
        row[0] = "C" + courseID;
        tableModel.addRow(row);
    }

    private void getAllCourses() {
        CourseDAO cdao = new CourseDAO();
        ArrayList<Integer> ids = cdao.getAllCourseIDs();
        Course course;

        for (int id : ids) {
            course = cdao.getCoursebyId(id);
            this.courses.add(course);
        }
    }

    private void addAllCoursestoTable() {
        String id, coursename, instructor, credits;
        Lecturer lec;
        if (courses.isEmpty()) {
            getAllCourses();
        }

        for (Course course : courses) {
            id = String.valueOf(course.getCourseid());
            coursename = course.getCoursename();
            lec = course.getLecturer();
            if (lec == null) {
                instructor = "None";
            } else {
                instructor = lec.getname();
            }
            credits = String.valueOf(course.getCredit());
            addtoTable(id, coursename, instructor, credits);
        }
    }

    private void getInstructorChoices() {
        EnrollmentDAO edao = new EnrollmentDAO();
        LecturerDAO ldao = new LecturerDAO();

        ArrayList<Integer> lecids = edao.getAvailableLecturesID();
        Lecturer lec;
        this.lecturers = new LinkedList<>();
        for (Integer lecid : lecids) {
            lec = ldao.getLecturerByID(lecid);
            lecturers.add(lec);
        }
    }

    private int editCourseName(String newName, int courseid) {
        return new CourseDAO().editCourseField("coursename", newName, courseid);
    }

    private int editCourseLec(int lecid, int courseid) {
        return new EnrollmentDAO().assignLectoCourse(lecid, courseid);

    }

    private int editCourseCredits(String newcredits, int courseid) {
        return new CourseDAO().editCourseField("credit", newcredits, courseid);

    }
}

