package GUI;

import javax.swing.*;
import java.awt.*;

public class CoursePage extends JPanel {
    public CoursePage() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        GUIHelper.setHeader(this, "Course Management");
    }
}
