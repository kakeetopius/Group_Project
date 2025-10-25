package GUI.Pages;

import BaseClasses.Course;
import BaseClasses.Student;
import DBAccess.CourseDAO;
import DBAccess.StudentDAO;
import GUI.CustomComponents.CustomTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class AnalyticsPage extends DataPages {

    //----------------GUI elements----------------------
    private JComboBox<String> chartComboBox;
    private int chartComboChecker = -1;
    private JPanel chartPanel;  //Panel for the charts
    private CardLayout cardLayout;  //layout to switch between the charts

    public AnalyticsPage() {
        super("Analytics Page");

        //------------------combo box to select the charts for viewing---------------------
        JLabel comboLabel = new JLabel("Select Chart: ");
        comboLabel.setFont(new Font("Arial", Font.BOLD, 12));
        comboLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        chartComboBox = new JComboBox<>();
        chartComboBox.setPreferredSize(new Dimension(200,10));
        chartComboBox.setMaximumSize(new Dimension(220,30));
        chartComboBox.setMinimumSize(new Dimension(150,30));
        chartComboBox.setEnabled(false);

        leftPanel.add(Box.createVerticalStrut(80));
        leftPanel.add(comboLabel);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(chartComboBox);

        //=============Card Layout to switch between charts============
        cardLayout = new CardLayout();
        chartPanel = new JPanel(cardLayout);
        add(chartPanel, BorderLayout.CENTER);

        //=======Action Listeners=============
        selectionBox.addActionListener(e -> updateCharts());
        chartComboBox.addActionListener(e -> checkChartSelection());
    }

    private JPanel createPieChart(DefaultPieDataset dataset, String title) {
        JFreeChart pieChart = ChartFactory.createPieChart(
                title,
                dataset,
                true,true,false
        );

        ChartPanel pieChartPanel = new ChartPanel(pieChart);
        pieChartPanel.setPreferredSize(new Dimension(400, 400));
        return pieChartPanel;
    }

    private JPanel createBarChart(DefaultCategoryDataset dataset, String title,String xAxis, String yAxis) {
        JFreeChart barChart = ChartFactory.createBarChart(title,xAxis,yAxis,dataset);

        ChartPanel barChartPanel = new ChartPanel(barChart);
        barChartPanel.setPreferredSize(new Dimension(400, 400));
        return barChartPanel;
    }

    private void updateCharts() {
        chartComboBox.removeAllItems();
        chartPanel.removeAll();  // Remove all cards
        chartPanel.revalidate(); // Refresh layout
        chartPanel.repaint();    // Redraw UI

        if (studentRadio.isSelected()) {
            chartComboBox.setEnabled(true);
            chartComboBox.addItem("Pie Chart For Student Grade Distribution");
            chartComboBox.addItem("Bar Chart For Student Grades");
        }
        else if (courseRadio.isSelected()) {
            chartComboBox.setEnabled(true);
            chartComboBox.addItem("Pie Chart For Grade Distribution For Course");
            chartComboBox.addItem("Performance for Course");
            chartComboBox.addItem("Bar Chart For Average Mark per Course");
            chartComboBox.addItem("Pie Chart For Enrollments per Course");
            chartComboBox.addItem("Bar Chart For Total Students Enrolled per Course");
        }
        else {
            JOptionPane.showMessageDialog(this, "First select Student or Course");
        }
    }

    private void updateStudentCharts() {
        //get selected student
        if (selectionBox.getSelectedIndex() == -1) {
            return;
        }

        //selected student from the selection Box
        int selectedIndex = selectionBox.getSelectedIndex();
        //---getting student object form the course list using the index of combo box since order is the same-----------
        Student student = super.allStudents.get(selectedIndex);
        Map<String,Integer> grades = student.getGrades();

        //=========Bar Chart=========================
        DefaultCategoryDataset datasetForBarChart = new DefaultCategoryDataset();

        for (String key : grades.keySet()) {
            datasetForBarChart.addValue(grades.get(key), "Marks", key);
        }
        JPanel barchart = createBarChart(datasetForBarChart,"Grades For "+student.getname(),"Course","Marks");

        //=============PieChart=====================
        DefaultPieDataset datasetForPieChart = new DefaultPieDataset();
        int[] valuesArray = new int[grades.size()];
        int i = 0;
        for (int value : grades.values()) {
            valuesArray[i] = value;
            i++;
        }
        int [] gradeDistribution = getGradeDistribution(valuesArray);

        datasetForPieChart.setValue("A",gradeDistribution[0]);
        datasetForPieChart.setValue("B",gradeDistribution[1]);
        datasetForPieChart.setValue("C",gradeDistribution[2]);
        datasetForPieChart.setValue("D",gradeDistribution[3]);
        datasetForPieChart.setValue("F",gradeDistribution[4]);

        JPanel piechart = createPieChart(datasetForPieChart,"Grade Distribution For "+student.getname());

        //adding charts to the chart panel
        chartPanel.add(piechart, "PieChart");
        chartPanel.add(barchart, "BarChart");

        //checking which chart was chosen
        if (chartComboChecker == 0) {
            cardLayout.show(chartPanel, "PieChart");
        }
        else if (chartComboChecker == 1) {
            cardLayout.show(chartPanel, "BarChart");
        }
    }

    private void updateCoursesCharts() {
        if (selectionBox.getSelectedIndex() == -1) {
            return;
        }

        int selectedIndex = selectionBox.getSelectedIndex();
        //---getting course object form the course list using the index of combo box since order is the same-----------
        Course course = super.allCourses.get(selectedIndex);
        //map of ids to grades
        Map<Integer,Integer> grades = course.getGrades();
        //getting student names from their ids.
        String[] studentNames = new String[grades.size()];
        int i = 0;
        StudentDAO sdao = new StudentDAO();
        for(int key : grades.keySet()) {
            Student s = sdao.getStudentByID(key);
            studentNames[i] = s.getname();
            i++;
        }

        //===============Pie Chart for grade distribution=================
        int[] valuesArray = new int[grades.size()];

        i = 0;
        for (int grade : grades.values()) {
            valuesArray[i] = grade;
            i++;
        }
        int [] gradeDistribution = getGradeDistribution(valuesArray);
        DefaultPieDataset datasetForPieChart = new DefaultPieDataset();
        datasetForPieChart.setValue("A",gradeDistribution[0]);
        datasetForPieChart.setValue("B",gradeDistribution[1]);
        datasetForPieChart.setValue("C",gradeDistribution[2]);
        datasetForPieChart.setValue("D",gradeDistribution[3]);
        datasetForPieChart.setValue("F",gradeDistribution[4]);

        JPanel piechart = createPieChart(datasetForPieChart,"Grade Distribution For "+course.getCoursename());
        chartPanel.add(piechart, "PieChart");

        //=================Performance Table=======================
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Student ID","Student Name","Mark"});
        String id,name,mark;
        int j = 0;
        for (int key : grades.keySet()) {
            id = "S"+key;
            name = studentNames[j];
            mark = grades.get(key).toString();
            model.addRow(new String[]{id,name,mark});
            j++;
        }
        CustomTable performanceTable = new CustomTable(model);
        JScrollPane scrollPane = new JScrollPane(performanceTable);

        //---------Adding all charts to the chartpanel-------------------
        chartPanel.add(scrollPane, "Performance Table");
        chartPanel.add(createAveMarkBarChart(),"Average Mark Per Course");
        chartPanel.add(createPieChartForEnrollments(),"Enrollment Per Course");
        chartPanel.add(createBarChartForEnrollments(),"Total Students Enrolled Per Course");

        //-----------------Flipping through the charts based on which was chosen---------------
        if (chartComboChecker == 2) {
            cardLayout.show(chartPanel, "PieChart");
        }
        else if (chartComboChecker == 3) {
            cardLayout.show(chartPanel, "Performance Table");
        }
        else if (chartComboChecker == 4) {
            cardLayout.show(chartPanel, "Average Mark Per Course");
        }
        else if (chartComboChecker == 5) {
            cardLayout.show(chartPanel, "Enrollment Per Course");
        }
        else if (chartComboChecker == 6) {
            cardLayout.show(chartPanel, "Total Students Enrolled Per Course");
        }
    }

    private JPanel createAveMarkBarChart() {
        CourseDAO cdao = new CourseDAO();
        //========Creating Bar Chart============
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Course course : super.allCourses) {
            dataset.addValue(course.calculateAverageGrade(),"Average Mark",course.getCoursename());
        }
        JPanel barChart = createBarChart(dataset,"Average Mark Per Course","Course","Average Mark");
        return barChart;
    }

    private JPanel createBarChartForEnrollments() {
        String coursename;
        int num;
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Course course : super.allCourses) {
            coursename = course.getCoursename();
            num = course.getStudentsCount();
            dataset.addValue(num,"Enrollment",coursename);
        }
        JPanel barChart = createBarChart(dataset,"Enrollment Per Course","Course","Enrollments");
        return barChart;
    }

    private JPanel createPieChartForEnrollments() {
        String name;
        int number;
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Course course : super.allCourses) {
            number = course.getStudentsCount();
            name = course.getCoursename();
            dataset.setValue(name,number);
        }
        JPanel pieChart = createPieChart(dataset,"Enrollment Per Course");
        return pieChart;
    }

    private int[] getGradeDistribution(int[] grades) {
        int A = 0;
        int B = 0;
        int C = 0;
        int D = 0;
        int F = 0;

        for (int grade : grades) {
            if (grade >= 70) {
                A++;
            }
            else if (grade >= 60) {
                B++;
            }
            else if (grade >= 50) {
                C++;
            }
            else if (grade >= 40) {
                D++;
            }
            else if (grade >= 0) {
                F++;
            }
        }

        int[] gradeDistribution = {A,B,C,D,F};
        return gradeDistribution;
    }

    private void checkChartSelection() {
        if (studentRadio.isSelected()) {
            if (chartComboBox.getSelectedIndex() == 0) {
                chartComboChecker = 0;  //Pie chart for grade distribution shows
            }
            else if (chartComboBox.getSelectedIndex() == 1) {
                chartComboChecker = 1; //bar chart for student grades shows
            }
            updateStudentCharts();
        }
        else if (courseRadio.isSelected()) {
            if (chartComboBox.getSelectedIndex() == 0) {
                chartComboChecker = 2; //pie chart for course grade distribution
            }
            else if (chartComboBox.getSelectedIndex() == 1) {
                chartComboChecker = 3; //table for course performance
            }
            else if (chartComboBox.getSelectedIndex() == 2) {
                chartComboChecker = 4; //bar chart for average mark per course
            }
            else if (chartComboBox.getSelectedIndex() == 3) {
                chartComboChecker = 5; //pie chart for enrollment per course
            }
            else if (chartComboBox.getSelectedIndex() == 4) {
                chartComboChecker = 6; //bar chart for total enrollments per course
            }
            updateCoursesCharts();
        }
    }
}
