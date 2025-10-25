package GUI.Pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginPage extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private ImageIcon bgImage, logoImage;

    public LoginPage(ActionListener actionListener) {
        this.setLayout(new BorderLayout());

        // Load Background Image
        bgImage = new ImageIcon(getClass().getResource("/pics/back2.jpg"));
        Image scaledImg = bgImage.getImage().getScaledInstance(1920, 1080, Image.SCALE_SMOOTH);
        ImageIcon newBgIcon = new ImageIcon(scaledImg);
        JLabel background = new JLabel(newBgIcon);

        background.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ===== GUI.Login Panel =====
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(new Color(255, 255, 255, 180)); // Semi-transparent white
        loginPanel.setPreferredSize(new Dimension(350, 350));

        // ===== Logo =====
        logoImage = new ImageIcon(getClass().getResource("/pics/logo.png")); // Load Logo
        JLabel logoLabel = new JLabel(logoImage);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER; // Center logo
        loginPanel.add(logoLabel, gbc);

        // ===== Username Label & Field =====
        JLabel userLabel = new JLabel("Username:");
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(userLabel, gbc);

        usernameField = new JTextField(15);
        usernameField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2,true));
        userLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 1;
        loginPanel.add(usernameField, gbc);


        // ===== Password Label & Field =====
        JLabel passLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        loginPanel.add(passLabel, gbc);
        passLabel.setFont(new Font("Arial", Font.BOLD, 16));

        passwordField = new JPasswordField(15);
        passwordField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2,true));
        gbc.gridx = 1;
        loginPanel.add(passwordField, gbc);

        // ===== GUI.Login Button =====
        JButton loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        loginButton.setBackground(new Color(70, 100, 100));
        loginButton.setBorder(BorderFactory.createLineBorder(new Color(70, 100, 100),2,true));

        loginButton.addActionListener(actionListener);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(loginButton, gbc);

        // Add login panel to the background
        background.add(loginPanel);

        // Add background label to the main panel
        add(background, BorderLayout.CENTER);
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void resetFields() {
        usernameField.setText(""); // Clear username
        passwordField.setText(""); // Clear password
    }

}
