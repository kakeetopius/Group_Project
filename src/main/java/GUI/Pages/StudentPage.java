package GUI.Pages;

import BaseClasses.Course;
import BaseClasses.Student;
import DBAccess.CourseDAO;
import DBAccess.EnrollmentDAO;
import DBAccess.PersonDAO;
import DBAccess.StudentDAO;
import GUI.CustomComponents.CustomTable;
import GUI.CustomComponents.CustomePanel;
import GUI.GuiUtils.GUIHelper;
import GUI.GuiUtils.InfoPopUP;
import GUI.GuiUtils.InputPopUP;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class StudentPage extends CustomePanel {
    private CustomTable studentTable;
    private DefaultTableModel tableModel;
    private JButton viewButton, addButton, deleteButton, enrollButton;
    private ArrayList<Student> students = new ArrayList<>();


    public StudentPage() {
        super("Student Management Page");
        //--------Student Table--------------
        String[] columnnames = {"StudentID", "Student Name","Email"};

        tableModel = new DefaultTableModel(columnnames,0);
        studentTable = new CustomTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        add(scrollPane, BorderLayout.CENTER);
        addAllStudentstoTable();

        //--------Button Panel------------------
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        viewButton = new JButton("View Student Details");
        addButton = new JButton("Add Student");
        enrollButton = new JButton("Enroll Student");
        deleteButton = new JButton("Remove Student");

        buttonPanel.add(viewButton);
        buttonPanel.add(addButton);
        buttonPanel.add(enrollButton);
        buttonPanel.add(deleteButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(buttonPanel, BorderLayout.SOUTH);

        //----------Event Listeners----------------
        viewButton.addActionListener(e->viewStudent());
        addButton.addActionListener(e->addStudent());
        deleteButton.addActionListener(e->deleteStudent());
        enrollButton.addActionListener(e->enrollStudent());
    }

    private void viewStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if(selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a student");
            return;
        }
        String id = studentTable.getValueAt(selectedRow, 0).toString();
        int stdid = Integer.parseInt(id.split("S")[1]);

        Student student = new StudentDAO().getStudentByID(stdid);
        JLabel name = new JLabel("Student Name: " + student.getname());
        JLabel studentid = new JLabel("Student ID: " + id);
        JLabel email = new JLabel("Email: " + student.getemail());
        JLabel Courses = new JLabel("Courses Enrolled: " );
        ArrayList<JLabel> coursenames = new ArrayList<>();
        for (Course c : student.getCourses()) {
            coursenames.add(new JLabel("         "+c.getCoursename()));
        }

        LinkedList<JComponent> components = new LinkedList<>();
        components.add(name);
        components.add(studentid);
        components.add(email);
        components.add(Courses);
        components.addAll(coursenames);

        GridBagConstraints gbc = GUIHelper.getGBC();
        InfoPopUP p = GUIHelper.createInfoPopUP("Student Details",gbc,components);
        p.setVisible(true);
    }

    private void addStudent() {
        InputPopUP p = GUIHelper.createInputPopUP("Add New Student");
        JTextField firstnameField = new JTextField();
        JTextField lastnameField = new JTextField();
        JTextField emailField = new JTextField();
        JComboBox<String> gendercombobox = new JComboBox<>(new String[]{"Male", "Female"});
        JTextField dobField = new JTextField();
        JTextField phoneField = new JTextField();

        GridBagConstraints gbc = GUIHelper.getGBC();

        p.addField("First Name: ", firstnameField, gbc);
        p.addField("Last Name: ", lastnameField, gbc);
        p.addField("Email: ", emailField, gbc);
        p.addField("Gender: ", gendercombobox, gbc);
        p.addField("Date of Birth(yyyy-mm-dd): ", dobField, gbc);
        p.addField("Phone Number: ", phoneField, gbc);
        p.setVisible(true);

        String firstname = firstnameField.getText();
        String lastname = lastnameField.getText();
        String email = emailField.getText();
        String dob = dobField.getText();
        String phone = phoneField.getText();

        if(firstname.isEmpty() || lastname.isEmpty() || email.isEmpty() || gendercombobox.getSelectedIndex()==-1 || dob.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill all the fields");
            return;
        }
        if(p.isConfirmed()) {
            String gender = gendercombobox.getSelectedItem().toString();
            Student student = new Student(firstname, lastname, gender, email);
            student.setdob(dob);
            student.setphone(phone);

            if (new StudentDAO().addStudent(student) == 0) {
                JOptionPane.showMessageDialog(this, "Student Added Successfully");
                student = (Student) new PersonDAO().getPersonByEmail(email);
                addtoTable("S" + student.getStdid(), student.getname(), student.getemail());
            } else {
                JOptionPane.showMessageDialog(this, "Error Adding Student");
            }
        }
    }


    private void enrollStudent() {
        InputPopUP p = GUIHelper.createInputPopUP("Enroll Student to a Course");
        ArrayList<Integer> availableCourseids;
        CourseDAO cdao = new CourseDAO(); //to get course objects from database
        EnrollmentDAO edao = new EnrollmentDAO(); //to add student to enrollment in database
        availableCourseids = cdao.getAllCourseIDs();
        LinkedList<Course> availableCourses = new LinkedList<>();//to store all course objects got from DB

        //getting course objects
        for (int id : availableCourseids) {
            availableCourses.add(cdao.getCoursebyId(id));
        }
        //getting coursenames to display
        String[] coursenames = new String[availableCourses.size()];
        for (int i = 0; i < coursenames.length; i++) {
            coursenames[i] = availableCourses.get(i).getCoursename();
        }

        //comboBox to display courses.
        JComboBox<String> coursenameComboBox = new JComboBox<>(coursenames);
        GridBagConstraints gbc = GUIHelper.getGBC();

        //getting selected row of student tab;le
        int selectedRow = studentTable.getSelectedRow();
        if(selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to enroll");
            return;
        }
        String id = studentTable.getValueAt(selectedRow, 0).toString();
        int stdid = Integer.parseInt(id.split("S")[1]); //student id without the S at the start

        //adding components to pop Up
        p.addField("Available Courses: ", coursenameComboBox, gbc);
        p.setVisible(true);

        //checking if OK button is clicked
        if (p.isConfirmed()) {
            //getting data from Course comboBox
            int selectedCourseIndex = coursenameComboBox.getSelectedIndex();
            if (selectedCourseIndex == -1) {
                JOptionPane.showMessageDialog(this, "No Course Selected");
                return;
            }

            int selectedCourseID = availableCourseids.get(selectedCourseIndex);
            System.out.println("Selected Course ID: " + selectedCourseID);
            if (edao.addStudenttoCourse(stdid, selectedCourseID) == 0) {
                JOptionPane.showMessageDialog(this, "Student Enrolled Successfully to Course");
            } else {
                JOptionPane.showMessageDialog(this, "Error Enrolling Student\nPerhaps Student is enrolled to this course already");
            }
        }
    }

    private void deleteStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a Student to be deleted");
            return;
        }
        String stdid = studentTable.getValueAt(selectedRow, 0).toString();
        int id = Integer.parseInt(stdid.split("S")[1]);

        if (new StudentDAO().deleteStudentById(id) == 0) {
            JOptionPane.showMessageDialog(this, "Student Deleted Successfully");
            tableModel.removeRow(selectedRow);
        }
        else {
            JOptionPane.showMessageDialog(this, "Error Deleting Student.\nStudent might be enrolled to a course");
        }
    }

    private void addtoTable(String stdid, String name, String email) {
        Object[] row = {stdid, name, email};
        tableModel.addRow(row);
    }

    private void getAllStudents() {
        StudentDAO sdao = new StudentDAO();
        ArrayList<Integer> ids = sdao.getAllStudentIDs();
        Student student;

        for (int id : ids) {
            student = sdao.getStudentByID(id);
            this.students.add(student);
        }
    }

    private void addAllStudentstoTable() {
        String stdid, name, email;
        if (students.isEmpty()) {
            getAllStudents();
        }

        for (Student s : students) {
            stdid = "S" + s.getStdid();
            name = s.getname();
            email = s.getemail();
            addtoTable(stdid, name, email);
        }
    }
}
