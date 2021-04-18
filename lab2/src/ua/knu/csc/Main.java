package ua.knu.csc;

import ua.knu.csc.core.Manager;
import ua.knu.csc.ui.DepartmentPanel;
import ua.knu.csc.ui.EmployeePanel;

import java.sql.SQLException;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Main {

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost/departments";
        String user = "postgres";
        String password = "postgres";

        try {
            Manager manager = new Manager(url, user, password);

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    createAndShowGUI(manager);
                }
            });
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    private static void createAndShowGUI(Manager manager) {
        JFrame mainWindow = new JFrame("lab2");
        mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.add("Employees", new EmployeePanel(manager));
        tabbedPane.add("Departments", new DepartmentPanel(manager));

        mainWindow.add(tabbedPane);

        mainWindow.pack();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        mainWindow.setLocation(screenSize.width / 2 - mainWindow.getSize().width / 2, screenSize.height / 2 - mainWindow.getSize().height / 2);

        mainWindow.setVisible(true);
    }
}
