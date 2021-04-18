package ua.knu.csc.ui;

import ua.knu.csc.core.Manager;
import ua.knu.csc.entity.Department;
import ua.knu.csc.entity.Employee;

import java.util.List;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class DepartmentPanel extends JPanel {
    private final Manager manager;

    private final JComboBox<String> departmentIdsComboBox = new JComboBox<>();

    private final JTextField departmentIdTextField = new JTextField();
    private final JTextField nameTextField = new JTextField();

    private final JButton buttonAdd = new JButton("Add");
    private final JButton buttonUpdate = new JButton("Update");
    private final JButton buttonDelete = new JButton("Delete");
    private final JButton buttonRefresh = new JButton("Refresh");

    private final DefaultTableModel defaultTableModel = new DefaultTableModel();
    private final JTable employeesTable = new JTable(defaultTableModel);

    public DepartmentPanel(Manager manager) {
        this.manager = manager;

        setLayout(new FlowLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 5, 5));

        panel.add(new JLabel("Choose a department ID"));
        panel.add(departmentIdsComboBox);

        panel.add(new JLabel("Department ID"));
        panel.add(departmentIdTextField);

        panel.add(new JLabel("Name"));
        panel.add(nameTextField);

        JPanel controlPanel = new JPanel();
        controlPanel.add(buttonAdd);
        controlPanel.add(buttonDelete);
        controlPanel.add(buttonUpdate);
        controlPanel.add(buttonRefresh);

        Box box = Box.createVerticalBox();
        box.add(panel);
        box.add(controlPanel);
        box.add(new JScrollPane(employeesTable));

        add(box);

        buttonRefresh.setToolTipText("Incorporates changes from a database.");

        Object[] columnIdentifiers = {"employee_id", "forename", "surname", "salary", "department_id"};
        defaultTableModel.setColumnIdentifiers(columnIdentifiers);

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

        buttonRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshDepartmentIdsComboBox();
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

                defaultTableModel.setRowCount(0);

                for (Employee employee : department.getEmployees()) {
                    Object[] rowData = {employee.getEmployeeId(), employee.getForename(), employee.getSurname(), employee.getSalary(), employee.getDepartmentId()};
                    defaultTableModel.addRow(rowData);
                }
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
