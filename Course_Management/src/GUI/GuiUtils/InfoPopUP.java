package GUI.GuiUtils;

import GUI.CustomComponents.PopUP;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class InfoPopUP extends PopUP {
    public InfoPopUP(JFrame parent,String title,GridBagConstraints gbc, LinkedList<JComponent> components) {
       super(parent, title);
        gbc.gridx = 0;
        gbc.gridy++;
        // Add received components
        if (components != null) {
            for (Component component : components) {
                if (component instanceof JLabel) {
                    component.setFont(new Font("Arial", Font.PLAIN, 14));
                }
                mainpanel.add(component, gbc);
                gbc.gridy++;
            }
        }

        pack();
    }


}
