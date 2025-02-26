package GUI;

import javax.swing.*;
import java.awt.*;

public class HomePage extends JPanel {
    public HomePage() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        GUIHelper.setHeader(this,"Welcome Admin!");
        // ===== Stats Panel =====
        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 70, 20));

        statsPanel.add(createStatCard("Total Students", "1,245"));
        statsPanel.add(createStatCard("Total Courses", "78"));
        statsPanel.add(createStatCard("Total Instructors", "42"));
        statsPanel.add(createStatCard("Active Enrollments", "3,587"));

        add(statsPanel, BorderLayout.CENTER);
    }


    // Helper method to create statistic panels
    private JPanel createStatCard(String title, String value) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setForeground(Color.DARK_GRAY);

        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 20));
        valueLabel.setForeground(new Color(70, 130, 180));

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(valueLabel, BorderLayout.CENTER);
        return panel;
    }

}
