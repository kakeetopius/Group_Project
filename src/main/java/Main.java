import GUI.MainGuiFrame;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            // try {
            //     UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            // } catch (Exception ex) {
            //     System.out.println(ex.getMessage());
            // }
            new MainGuiFrame().setVisible(true);
        });
    }
}
