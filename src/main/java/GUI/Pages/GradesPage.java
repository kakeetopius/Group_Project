package GUI.Pages;

import BaseClasses.Course;
import BaseClasses.Student;
import DBAccess.CourseDAO;
import DBAccess.EnrollmentDAO;
import DBAccess.StudentDAO;
import GUI.CustomComponents.CustomTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.LinkedList;
import java.util.Map;

public class GradesPage extends DataPages {
    private JButton saveButton;
    private JButton cancelButton;
    private JTable marksTable;
    private DefaultTableModel tableModel;

    public GradesPage() {
        super("Grade Management");

        //-----------------center panel - table for marks entry------------------
        tableModel = new DefaultTableModel();
        //making the marks column editable
        marksTable = new CustomTable(tableModel){
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };

        JScrollPane scrollPane = new JScrollPane(marksTable);

        //-------------bottomPanel for buttons------------------
        JPanel bottomPanel = new JPanel();
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");
        bottomPanel.add(saveButton);
        bottomPanel.add(cancelButton);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(e->saveMarks());
        cancelButton.addActionListener(e->updateTableData());
        selectionBox.addActionListener(e->updateTableData());
    }


    private void updateTableData() {
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);

        if (studentRadio.isSelected()) {
            tableModel.setColumnIdentifiers(new String[]{"CourseID","Course", "Marks"});
            int selectedIndex = selectionBox.getSelectedIndex();
            if (selectedIndex != -1) {
                Student student = allStudents.get(selectedIndex);
                Map<String, Integer> details = student.getGrades();
                CourseDAO cdao = new CourseDAO();
                for (String key : details.keySet()) {
                    String courseid = "C" + cdao.getCoursebyName(key).getCourseid();
                    tableModel.addRow(new String[]{courseid,key, details.get(key).toString()});
                }
            }
        }
        if (courseRadio.isSelected()) {
            tableModel.setColumnIdentifiers(new String[]{"StudentID","Student", "Marks"});
            int selectedIndex = selectionBox.getSelectedIndex();
            if (selectedIndex != -1) {
                Course course = allCourses.get(selectedIndex);
                Map<Integer, Integer> details = course.getGrades();
                StudentDAO sdao = new StudentDAO();
                String studentName;

                for (int key : details.keySet()) {
                    studentName = sdao.getStudentByID(key).getname();

                    tableModel.addRow(new String[]{"S"+key,studentName,details.get(key).toString()});
                }
            }
        }
    }

    public void saveMarks() {
        //getting marks from table after save is clicked
        LinkedList<Integer> marksAfterSave = new LinkedList<>();
        int mark;
        //initialising the list
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String String_mark = tableModel.getValueAt(i, 2).toString();
            try {
                mark = Integer.parseInt(String_mark);
                marksAfterSave.add(mark);
            }
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "One of the marks entered is invalid");
                return;
            }
        }

        //checking if marks are eligible
        for (int inputtedMark : marksAfterSave) {
            if (inputtedMark > 100 || inputtedMark < 0) {
                JOptionPane.showMessageDialog(this, "Invalid Mark");
                return;
            }
        }
        //if student radio button is selected
        if(studentRadio.isSelected()) {
            //posititon of the student selected
            int selectedIndex = selectionBox.getSelectedIndex();

            //if there is a student selected
            if (selectedIndex != -1) {
                //get student object from list using the index of the selectionBox
                Student student = allStudents.get(selectedIndex);
                //obtaining previous marks from the student object
                Map<String, Integer> previousMarks = student.getGrades();
                int i = 0;
                EnrollmentDAO edao = new EnrollmentDAO();;
                CourseDAO cdao = new CourseDAO();

                for (String key : previousMarks.keySet()) {
                    //comparing the previous marks with the marks entered the table.
                    if (previousMarks.get(key) != marksAfterSave.get(i)) {
                        //get course object from Db
                        Course course = cdao.getCoursebyName(key);
                        //get course id for the course whose mark has changed.
                        int courseID = course.getCourseid();
                        //add marks to database
                        if (edao.assignMarktoStudent(student.getStdid(),courseID,marksAfterSave.get(i)) == 0) {
                            JOptionPane.showMessageDialog(this,"Student marks successfully added to the course");
                        }
                        else {
                            JOptionPane.showMessageDialog(this,"Error assigning mark to student");
                        }
                    }
                    i++;
                }
            }
        }
        else if (courseRadio.isSelected()) {
            int selectedIndex = selectionBox.getSelectedIndex();
            if (selectedIndex != -1) {
                Course course = allCourses.get(selectedIndex);
                int courseid = course.getCourseid();
                //map for student id to mark
                Map<Integer, Integer> previousMarks = course.getGrades();
                int i = 0;
                EnrollmentDAO edao = new EnrollmentDAO();
                for(int key : previousMarks.keySet()) {
                    if(previousMarks.get(key) != marksAfterSave.get(i)) {

                        if(edao.assignMarktoStudent(key,courseid,marksAfterSave.get(i)) == 0) {
                            JOptionPane.showMessageDialog(this,"Student marks successfully");

                        }
                        else {
                            JOptionPane.showMessageDialog(this,"Error assigning mark to student");
                        }
                    }
                    i++;
                }
            }
        }
    }
}
