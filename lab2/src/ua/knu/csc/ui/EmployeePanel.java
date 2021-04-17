package ua.knu.csc.ui;

import ua.knu.csc.core.Manager;
import ua.knu.csc.entity.Employee;

import java.util.List;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EmployeePanel extends JPanel {
    private final Manager manager;

    private final JComboBox<String> employeeIdsComboBox = new JComboBox<>();

    private final JTextField employeeIdTextField = new JTextField();
    private final JTextField forenameTextField = new JTextField();
    private final JTextField surnameTextField = new JTextField();
    private final JTextField salaryTextField = new JTextField();
    private final JTextField departmentIdTextField = new JTextField();

    private final JButton buttonAdd = new JButton("Add");
    private final JButton buttonUpdate = new JButton("Update");
    private final JButton buttonDelete = new JButton("Delete");

    public EmployeePanel(Manager manager) {
        this.manager = manager;

        setLayout(new GridLayout(8, 2));

        add(new JLabel("choose employee id"));
        add(employeeIdsComboBox);

        add(new JLabel("employee id"));
        add(employeeIdTextField);

        add(new JLabel("forename"));
        add(forenameTextField);

        add(new JLabel("surname"));
        add(surnameTextField);

        add(new JLabel("salary"));
        add(salaryTextField);

        add(new JLabel("department id"));
        add(departmentIdTextField);

        add(buttonAdd);
        add(buttonDelete);

        add(buttonUpdate);

        employeeIdsComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCurrentSelectedEmployee();
            }
        });

        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEmployee();
            }
        });

        buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteEmployee();
            }
        });

        buttonUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateEmployee();
            }
        });

        refreshEmployeeIdsComboBox();
    }

    private void showCurrentSelectedEmployee() {
        String employeeId = (String) employeeIdsComboBox.getSelectedItem();

        if (employeeId != null) {
            Employee employee = manager.getEmployee(employeeId);

            if (employee != null) {
                employeeIdTextField.setText(employee.getEmployeeId());

                forenameTextField.setText(employee.getForename());
                surnameTextField.setText(employee.getSurname());
                salaryTextField.setText(String.valueOf(employee.getSalary()));

                departmentIdTextField.setText(employee.getDepartmentId());
            }
        }
    }

    private void refreshEmployeeIdsComboBox() {
        employeeIdsComboBox.removeAllItems();

        List<String> allEmployeeIds = manager.getAllEmployeeIds();

        for (String employeeId : allEmployeeIds) {
            employeeIdsComboBox.addItem(employeeId);
        }
    }

    private void addEmployee() {
        String employeeId = employeeIdTextField.getText();

        boolean isSuccessfullyAdded = manager.addEmployee(employeeId, forenameTextField.getText(),
                surnameTextField.getText(), Long.parseLong(salaryTextField.getText()),
                departmentIdTextField.getText());

        if (isSuccessfullyAdded) {
            employeeIdsComboBox.addItem(employeeId);

            if (employeeIdsComboBox.getItemCount() > 1) {
                employeeIdsComboBox.setSelectedItem(employeeId);
            }
        }
    }

    private void updateEmployee() {
        manager.updateEmployee(employeeIdTextField.getText(), forenameTextField.getText(),
                surnameTextField.getText(), Long.parseLong(salaryTextField.getText()),
                departmentIdTextField.getText());
    }

    private void deleteEmployee() {
        String employeeId = employeeIdTextField.getText();

        boolean isSuccessfullyDeleted = manager.deleteEmployee(employeeId);

        if (isSuccessfullyDeleted) {
            employeeIdsComboBox.removeItem(employeeId);
        }
    }
}
