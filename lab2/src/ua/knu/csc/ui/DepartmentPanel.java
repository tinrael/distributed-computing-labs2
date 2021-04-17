package ua.knu.csc.ui;

import ua.knu.csc.core.Manager;
import ua.knu.csc.entity.Department;

import java.util.List;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        departmentIdsComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCurrentSelectedDepartment();
            }
        });

        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addDepartment();
            }
        });

        buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteDepartment();
            }
        });

        buttonUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDepartment();
            }
        });

        refreshDepartmentIdsComboBox();
    }

    private void showCurrentSelectedDepartment() {
        String departmentId = (String) departmentIdsComboBox.getSelectedItem();

        if (departmentId != null) {
            Department department = manager.getDepartment(departmentId);

            if (department != null) {
                departmentIdTextField.setText(department.getDepartmentId());
                nameTextField.setText(department.getName());
            }
        }
    }

    private void refreshDepartmentIdsComboBox() {
        departmentIdsComboBox.removeAllItems();

        List<String> allDepartmentIds = manager.getAllDepartmentIds();

        for (String departmentId : allDepartmentIds) {
            departmentIdsComboBox.addItem(departmentId);
        }
    }

    private void addDepartment() {
        String departmentId = departmentIdTextField.getText();

        boolean isSuccessfullyAdded = manager.addDepartment(departmentId, nameTextField.getText());

        if (isSuccessfullyAdded) {
            departmentIdsComboBox.addItem(departmentId);

            if (departmentIdsComboBox.getItemCount() > 1) {
                departmentIdsComboBox.setSelectedItem(departmentId);
            }
        }
    }

    private void updateDepartment() {
        manager.updateDepartment(departmentIdTextField.getText(), nameTextField.getText());
    }

    private void deleteDepartment() {
        String departmentId = departmentIdTextField.getText();

        boolean isSuccessfullyDeleted = manager.deleteDepartment(departmentId);

        if (isSuccessfullyDeleted) {
            departmentIdsComboBox.removeItem(departmentId);
        }
    }
}
