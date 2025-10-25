package GUI.GuiUtils;

import GUI.MainGuiFrame;
import GUI.MainGuiFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

public class GUIHelper {
    public static MainGuiFrame mainGUI;//holds the instance of the main JFrame.


    //function to set the main JFrame from MainGui class
    public static void setMainGUI(MainGuiFrame gui) {
        mainGUI = gui;
    }


    public static void setHeader(JPanel panel, String title) {
        // ===== Header Panel (Welcome Banner) =====
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(0, 90, 180));
        headerPanel.setPreferredSize(new Dimension(0, 100));

        JLabel welcomeLabel = new JLabel(title, SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);

        // Date Label
        JLabel dateLabel = new JLabel(getCurrentDate(), SwingConstants.RIGHT);
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        dateLabel.setForeground(Color.WHITE);
        dateLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 2, 10));

        // ===== Logout Label  =====
        JLabel logoutLabel = new JLabel("LOGOUT", SwingConstants.LEFT);
        logoutLabel.setFont(new Font("Arial", Font.BOLD, 14));
        logoutLabel.setForeground(Color.WHITE);
        logoutLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutLabel.setBorder(BorderFactory.createEmptyBorder(2, 10, 10, 10));
        logoutLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                logout();
            }
        });

        // Panel for date, logout, and refresh
        JPanel topRightPanel = new JPanel();
        topRightPanel.setLayout(new BoxLayout(topRightPanel, BoxLayout.Y_AXIS));
        topRightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));
        topRightPanel.setBackground(new Color(0, 90, 180));
        topRightPanel.add(dateLabel);
        topRightPanel.add(logoutLabel);

        headerPanel.add(welcomeLabel, BorderLayout.CENTER);
        headerPanel.add(topRightPanel, BorderLayout.EAST);
        panel.add(headerPanel, BorderLayout.NORTH);
    }


    public static void logout() {
        if (mainGUI != null) {
            mainGUI.logout();
        } else {
            System.out.println("MainGUI instance is not set in LogoutUtil.");
        }
    }
    // Helper method to get current date
    public static String getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM dd, yyyy");
        String formattedDate = formatter.format(date);
        return formattedDate;
    }

   public static InputPopUP createInputPopUP(String title) {
        return new InputPopUP(mainGUI,title);
   }

   public static InfoPopUP createInfoPopUP(String title, GridBagConstraints gbc, LinkedList<JComponent> components) {
        return new InfoPopUP(mainGUI,title,gbc,components);
   }

    //for inital positioning of fields in the pop ups
    public static GridBagConstraints getGBC() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }
}


