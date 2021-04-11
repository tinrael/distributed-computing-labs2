package ua.knu.csc;

import com.company.departments.Department;
import com.company.departments.Employee;

import java.math.BigInteger;

import java.util.Iterator;
import java.util.Set;

public class Manager {
    private Set<Department> departments;

    private final DOMBuilder domBuilder = new DOMBuilder();

    public void loadFromFile(String filename) {
        departments = domBuilder.build(filename);
    }

    public void addDepartment(String id, String name) throws IllegalArgumentException {
        if (departments.stream().anyMatch(department -> department.getId().equals(id))) {
            throw new IllegalArgumentException("A department with the specified ID '" + id + "' already exists.");
        }

        Department department = new Department();

        department.setId(id);
        department.setName(name);

        departments.add(department);
    }

    public Department getDepartment(String id) throws IllegalArgumentException {
        for (Department department : departments) {
            if (department.getId().equals(id)) {
                return department;
            }
        }
        throw new IllegalArgumentException("A department with the specified ID '" + id + "' does not exist.");
    }

    public void deleteDepartment(String id) {
        Iterator<Department> iterator = departments.iterator();
        while (iterator.hasNext()) {
            Department department = iterator.next();
            if (department.getId().equals(id)) {
                iterator.remove();
                return;
            }
        }
    }

    public void addEmployee(String employeeId, String forename, String surname, BigInteger salary, String departmentId) throws IllegalArgumentException {
        for (Department department : departments) {
            for (Employee employee : department.getEmployee()) {
                if (employee.getId().equals(employeeId)) {
                    throw new IllegalArgumentException("An employee with the specified ID '" + employeeId + "' already exists.");
                }
            }
        }

        Department department = getDepartment(departmentId);

        Employee employee = new Employee();
        employee.setId(employeeId);
        employee.setForename(forename);
        employee.setSurname(surname);
        employee.setSalary(salary);

        department.getEmployee().add(employee);
    }

    public Employee getEmployee(String id) throws IllegalArgumentException {
        for (Department department : departments) {
            for (Employee employee : department.getEmployee()) {
                if (employee.getId().equals(id)) {
                    return employee;
                }
            }
        }
        throw new IllegalArgumentException("An employee with the specified ID '" + id + "' does not exist.");
    }

    public void deleteEmployee(String id) {
        for (Department department : departments) {
            Iterator<Employee> iterator = department.getEmployee().iterator();
            while (iterator.hasNext()) {
                Employee employee = iterator.next();
                if (employee.getId().equals(id)) {
                    iterator.remove();
                    return;
                }
            }
        }
        throw new IllegalArgumentException("An employee with the specified ID '" + id + "' does not exist.");
    }
}
