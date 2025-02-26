package GUI;

import javax.swing.*;
import java.awt.*;

public class GUI2 extends JFrame {
    private CardLayout cardLayout;
    private Login loginPanel;
    private AdminDash adminDashboard;

    public GUI2() {
        initComponents();
        cardLayout = (CardLayout) getContentPane().getLayout();
        cardLayout.show(getContentPane(), "login");
        GUIHelper.setMainGUI(this);
    }

    private void initComponents() {
        getContentPane().setLayout(new CardLayout());  //setting layout manager for the whole JFrame

        loginPanel = new Login(e->authenticateUser());
        getContentPane().add(loginPanel, "login");

        adminDashboard = new AdminDash();
        getContentPane().add(adminDashboard, "admin");

        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void authenticateUser() {
        String username = loginPanel.getUsername();
        String password = loginPanel.getPassword();

        if (username.equals("admin") && password.equals("admin")) {
            cardLayout.show(getContentPane(), "admin"); // Show admin dashboard
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials!", "GUI.Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void logout() {
        loginPanel.resetFields();
        cardLayout.show(getContentPane(), "login");
    }
}
