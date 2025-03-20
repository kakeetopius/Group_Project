package GUI.CustomComponents;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class CustomTable extends JTable {

    public CustomTable(DefaultTableModel model) {
        super(model);
        setGridColor(Color.LIGHT_GRAY);
        setShowGrid(true);

        JTableHeader header = getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBackground(new Color(0, 90, 180));
        header.setForeground(Color.black);

        setFont(new Font("SansSerif", Font.PLAIN, 15));
        setForeground(Color.DARK_GRAY);
        setSelectionBackground(new Color(200, 200, 255)); // Highlight color
        setSelectionForeground(Color.BLACK);

        setRowHeight(25); 
    }


    @Override
    public boolean isCellEditable(int row, int column) {
        return false; // Disable editing
    }
}

