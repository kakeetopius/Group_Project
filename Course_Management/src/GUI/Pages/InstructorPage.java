package GUI.Pages;

import BaseClasses.Course;
import BaseClasses.Lecturer;
import DBAccess.LecturerDAO;
import DBAccess.PersonDAO;
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

public class InstructorPage extends CustomePanel {
    private CustomTable LecTable;
    private DefaultTableModel tableModel;
    private JButton viewButton, addButton,deleteButton;
    ArrayList<Lecturer> lecturers = new ArrayList<>();

    public InstructorPage() {
        super("Instructor Management Page");

        //--------Lecturer Table--------------
        String[] columnnames = {"LecturerID", "Lecturer Name", "Email"};

        tableModel = new DefaultTableModel(columnnames, 0);
        LecTable = new CustomTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(LecTable);
        add(scrollPane, BorderLayout.CENTER);
        addAllLecturerstoTable();

        //--------Button Panel------------------
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        viewButton = new JButton("View Lecturer Details");
        addButton = new JButton("Add Lecturer");
        deleteButton = new JButton("Delete Lecturer");

        buttonPanel.add(viewButton);
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(buttonPanel, BorderLayout.SOUTH);

        //----------Event Listeners----------------
        viewButton.addActionListener(e -> viewLecDetails());
        addButton.addActionListener(e -> addLec());
        deleteButton.addActionListener(e -> deleteLec());
    }

    private void viewLecDetails() {
        int selectedRow = LecTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a lecturer");
            return;
        }
        String id = LecTable.getValueAt(selectedRow, 0).toString();
        int lecID = Integer.parseInt(id.split("L")[1]);//removing L from lecturer ID

        //getting lecturer object from database
        Lecturer lecturer = new LecturerDAO().getLecturerByID(lecID);

        //creating fields to display as output.
        JLabel name = new JLabel("Lecturer Name: " + lecturer.getname());
        JLabel lecid = new JLabel("Lecturer ID: " + id);
        JLabel email = new JLabel("Email: " + lecturer.getemail());

        //getting course for the lecturer.
        Course lecCourse = lecturer.getCourse();
        JLabel courseLabel;
        if (lecCourse == null) {
            courseLabel = new JLabel("Course Teaching: NONE");
        }
        else {
            courseLabel = new JLabel("Course Teaching: " + lecCourse.getCoursename());
        }

        //GridBagConstraints for positioning on the popUp
        GridBagConstraints gbc = GUIHelper.getGBC();

        //array of JComponents to add to the pop up
        LinkedList<JComponent> components = new LinkedList<>();
        components.add(name);
        components.add(lecid);
        components.add(email);
        components.add(courseLabel);

        //creating the info POPUP to show the info.
        InfoPopUP p = GUIHelper.createInfoPopUP("Lecturer Details",gbc,components);
        p.setVisible(true);
    }

    private void addLec() {
        InputPopUP p = GUIHelper.createInputPopUP("Add New Lecturer");
        JTextField firstnameField = new JTextField();
        JTextField lastnameField = new JTextField();
        JTextField emailField = new JTextField();
        JComboBox<String> gendercombobox = new JComboBox<>(new String[]{"Male", "Female"});
        JTextField dobField = new JTextField();
        JTextField phoneField = new JTextField();
        JComboBox<String> facultyBox = new JComboBox<>(new String[]{"SCES","SLS","SHSS","SIMS","SBS"});

        GridBagConstraints gbc = GUIHelper.getGBC();

        p.addField("First Name: ", firstnameField, gbc);
        p.addField("Last Name: ", lastnameField, gbc);
        p.addField("Email: ", emailField, gbc);
        p.addField("Gender: ", gendercombobox, gbc);
        p.addField("Date of Birth(yyyy-mm-dd): ", dobField, gbc);
        p.addField("Phone Number: ", phoneField, gbc);
        p.addField("Faculty: ", facultyBox, gbc);
        p.setVisible(true);

        String firstname = firstnameField.getText();
        String lastname = lastnameField.getText();
        String email = emailField.getText();
        String dob = dobField.getText();
        String phone = phoneField.getText();

        if(p.isConfirmed()) {
            if (firstname.isEmpty() || lastname.isEmpty() || email.isEmpty() || facultyBox.getSelectedIndex() == -1 || gendercombobox.getSelectedIndex() == -1 || dob.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all the fields");
                return;
            }
            String gender = gendercombobox.getSelectedItem().toString();
            String faculty = facultyBox.getSelectedItem().toString();

            Lecturer lec = new Lecturer(firstname, lastname, gender, email, faculty);
            lec.setdob(dob);
            lec.setphone(phone);

            if (new LecturerDAO().addLecturer(lec) == 0) {
                JOptionPane.showMessageDialog(this, "Lecturer Added Successfully");
                lec = (Lecturer) new PersonDAO().getPersonByEmail(email);
                addtoTable("L" + lec.getLecID(), lec.getname(), lec.getemail());
            } else {
                JOptionPane.showMessageDialog(this, "Error Adding Lecturer");
            }
        }
    }

    private void deleteLec() {
        int selectedRow = LecTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a Student to be deleted");
            return;
        }
        String stdid = LecTable.getValueAt(selectedRow, 0).toString();
        int id = Integer.parseInt(stdid.split("L")[1]);

        if (new LecturerDAO().deleteLecturerById(id) == 0) {
            JOptionPane.showMessageDialog(this, "Lecturer Deleted Successfully");
            tableModel.removeRow(selectedRow);
        }
        else {
            JOptionPane.showMessageDialog(this, "Error Deleting Lecturer.\nLecturer might be assigned to a course");
        }
    }

    private void addtoTable(String lecid, String name , String email) {
        Object[] row = {lecid, name, email};
        tableModel.addRow(row);
    }

    private void getAllLecturers() {
        LecturerDAO ldao = new LecturerDAO();
        ArrayList<Integer> ids = ldao.getAllLecIDs();
        Lecturer lec;

        for (int id : ids) {
            lec = ldao.getLecturerByID(id);
            this.lecturers.add(lec);
        }
    }

    private void addAllLecturerstoTable() {
        String id, name, email;
        if (lecturers.isEmpty()) {
            getAllLecturers();
        }
        for (Lecturer lec : lecturers) {
            id = "L" + lec.getLecID();
            name = lec.getname();
            email = lec.getemail();
            addtoTable(id,name,email);
        }
    }
}
