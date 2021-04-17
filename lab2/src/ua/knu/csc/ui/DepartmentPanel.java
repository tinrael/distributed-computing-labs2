package ua.knu.csc.ui;

import ua.knu.csc.core.Manager;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DepartmentPanel extends JPanel {
    private final Manager manager;

    private final JComboBox<String> departmentIdsComboBox = new JComboBox<>();

    private final JTextField departmentIdTextField = new JTextField();
    private final JTextField nameTextField = new JTextField();

    private final JButton buttonAdd = new JButton("Add");
    private final JButton buttonUpdate = new JButton("Update");
    private final JButton buttonDelete = new JButton("Delete");

    public DepartmentPanel(Manager manager) {
        this.manager = manager;

        setLayout(new GridLayout(5, 2));

        add(new JLabel("choose department id"));
        add(departmentIdsComboBox);

        add(new JLabel("department id"));
        add(departmentIdTextField);

        add(new JLabel("name"));
        add(nameTextField);

        add(buttonAdd);
        add(buttonDelete);

        add(buttonUpdate);
    }
}
