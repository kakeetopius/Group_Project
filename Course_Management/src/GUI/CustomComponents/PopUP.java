package GUI.CustomComponents;

import javax.swing.*;
import java.awt.*;

public class PopUP extends JDialog {
    protected JPanel mainpanel ;
    private boolean confirmed = false;//to check if user clicked ok

    public PopUP(JFrame parent, String title) {
        super(parent, title, true);
        setLayout(new BorderLayout());

        //=---Main Panel of the PopUP------------
        mainpanel = new JPanel(new GridBagLayout());
        mainpanel.setBorder(BorderFactory.createEmptyBorder(20,10,20,10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ===== Header Panel =====
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 90, 180));
        headerPanel.setPreferredSize(new Dimension(400, 40));
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);


        // ===== Buttons Panel =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        okButton.setFont(new Font("Arial", Font.BOLD, 16));
        cancelButton.setFont(new Font("Arial", Font.BOLD, 16));

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        // Button Actions
        okButton.addActionListener(e -> {
            confirmed = true; // Mark as confirmed
            dispose(); // Close dialog
        });

        cancelButton.addActionListener(e -> dispose());

        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        mainpanel.add(buttonPanel, gbc);

        add(headerPanel, BorderLayout.NORTH);
        add(mainpanel, BorderLayout.CENTER);

        // Set dialog properties
        //setSize(750, 300);
        pack();
        setLocationRelativeTo(parent);
        setResizable(true);
    }

    public Boolean isConfirmed() {
        return confirmed;
    }

}
