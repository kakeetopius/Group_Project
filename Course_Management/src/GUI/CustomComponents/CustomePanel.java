package GUI.CustomComponents;

import GUI.GuiUtils.GUIHelper;

import javax.swing.*;
import java.awt.*;

public class CustomePanel extends JPanel{

    public CustomePanel(String title) {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));
        setHeader(title);
    }

    public void setHeader(String title) {
        GUIHelper.setHeader(this, title);
    }

}
