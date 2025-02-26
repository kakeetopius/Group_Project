import GUI.GUI2;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            new GUI2().setVisible(true);
        });
    }
}
