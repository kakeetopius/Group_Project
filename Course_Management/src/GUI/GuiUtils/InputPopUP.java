package GUI.GuiUtils;

import GUI.CustomComponents.PopUP;

import javax.swing.*;
import java.awt.*;

public class InputPopUP extends PopUP {

    public InputPopUP(JFrame parent, String title) {
        super(parent, title);
    }

    public void addField(String label, JComponent field, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.BOLD, 14));
        super.mainpanel.add(lbl, gbc);

        field.setPreferredSize(new Dimension(200, 25));
        field.setFont(new Font("Arial", Font.PLAIN, 12));
        if (field instanceof JComboBox<?>) {
            ((JComboBox<?>) field).setSelectedItem(null); // Ensuring nothing is selected.
        }
        gbc.gridx = 1;
        super.mainpanel.add(field, gbc);
        pack();
    }

}
