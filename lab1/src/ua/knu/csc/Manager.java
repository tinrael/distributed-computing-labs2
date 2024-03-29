package ua.knu.csc;

import com.company.departments.Department;
import com.company.departments.Employee;

import java.util.Set;
import java.util.Iterator;

import java.math.BigInteger;

import java.io.File;

import org.w3c.dom.Document;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Manager {
    private Set<Department> departments;

    private final DOMBuilder domBuilder = new DOMBuilder();

    public synchronized void saveToFile(String xmlFilename) {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            Document document = domBuilder.createXmlDocument(departments);
            Source source = new DOMSource(document);
            Result result = new StreamResult(new File(xmlFilename));

            transformer.transform(source, result);
        } catch (TransformerConfigurationException e) {
            System.err.println("[manager]: TransformerConfigurationException: " + e.getMessage());
        } catch (TransformerException e) {
            System.err.println("[manager]: TransformerException: " + e.getMessage());
        }
    }

    public synchronized void loadFromFile(String xmlFileLocation) {
        departments = domBuilder.build(xmlFileLocation);
    }

    public synchronized void addDepartment(String id, String name) throws IllegalArgumentException {
        if (departments.stream().anyMatch(department -> department.getId().equals(id))) {
            throw new IllegalArgumentException("A department with the specified ID '" + id + "' already exists.");
        }

        Department department = new Department();

        department.setId(id);
        department.setName(name);

        departments.add(department);
    }

    public synchronized Department getDepartment(String id) throws IllegalArgumentException {
        for (Department department : departments) {
            if (department.getId().equals(id)) {
                return department;
            }
        }
        throw new IllegalArgumentException("A department with the specified ID '" + id + "' does not exist.");
    }

    public synchronized void deleteDepartment(String id) throws IllegalArgumentException {
        Iterator<Department> iterator = departments.iterator();
        while (iterator.hasNext()) {
            Department department = iterator.next();
            if (department.getId().equals(id)) {
                iterator.remove();
                return;
            }
        }
        throw new IllegalArgumentException("A department with the specified ID '" + id + "' does not exist.");
    }

    public synchronized void addEmployee(String employeeId, String forename, String surname, BigInteger salary, String departmentId) throws IllegalArgumentException {
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

    public synchronized Employee getEmployee(String id) throws IllegalArgumentException {
        for (Department department : departments) {
            for (Employee employee : department.getEmployee()) {
                if (employee.getId().equals(id)) {
                    return employee;
                }
            }
        }
        throw new IllegalArgumentException("An employee with the specified ID '" + id + "' does not exist.");
    }

    public synchronized void deleteEmployee(String id) throws IllegalArgumentException {
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

    public synchronized void print() {
        System.out.println(departments);
    }
}
