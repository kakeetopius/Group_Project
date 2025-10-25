package GUI;

import GUI.GuiUtils.GUIHelper;
import GUI.Pages.LoginPage;

import javax.swing.*;
import java.awt.*;

public class MainGuiFrame extends JFrame {
    private CardLayout cardLayout;
    private LoginPage loginPanel;
    private AdminDash adminDashboard;

    public MainGuiFrame() {
        initComponents();
        cardLayout = (CardLayout) getContentPane().getLayout();
        cardLayout.show(getContentPane(), "login");
        GUIHelper.setMainGUI(this); //setting the frame instance in the GUIHelper Utility class.
    }

    private void initComponents() {
        getContentPane().setLayout(new CardLayout());  //setting layout manager for the whole JFrame

        loginPanel = new LoginPage(e -> authenticateUser());
        getContentPane().add(loginPanel, "login");

        adminDashboard = new AdminDash();
        getContentPane().add(adminDashboard, "admin");

        pack();
	this.setSize(1920, 1080);
	// this.setResizable(false);  // prevent resizing
	this.setLocationRelativeTo(null); // center on screen
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void authenticateUser() {
        String username = loginPanel.getUsername();
        String password = loginPanel.getPassword();

        if (username.equals("admin") && password.equals("1234")) {
            cardLayout.show(getContentPane(), "admin"); // Show admin dashboard
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials!", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void logout() {
        loginPanel.resetFields();
        cardLayout.show(getContentPane(), "login");
    }
}
