package ua.knu.csc;

import com.company.departments.Department;
import com.company.departments.Employee;

import java.io.IOException;

import java.math.BigInteger;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class DOMBuilder {
    private final static String outputTextPrefix = "[dom-builder]: ";

    private DocumentBuilder documentBuilder;

    public DOMBuilder() {
        try {
            this.documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            System.err.println(outputTextPrefix + e.getMessage());
        }
    }

    public Set<Department> build(String xmlFileLocation) {
        Set<Department> departments = new HashSet<>();

        try {
            Document document = documentBuilder.parse(xmlFileLocation);
            Element rootElement = document.getDocumentElement();

            NodeList departmentElements = rootElement.getElementsByTagName("department");
            for (int i = 0; i < departmentElements.getLength(); i++) {
                Element departmentElement = (Element) departmentElements.item(i);

                Department department = createDepartment(departmentElement);

                NodeList employeeElements = departmentElement.getElementsByTagName("employee");
                for (int j = 0; j < employeeElements.getLength(); j++) {
                    Element employeeElement = (Element) employeeElements.item(j);

                    department.getEmployee().add(createEmployee(employeeElement));
                }

                departments.add(department);
            }
        } catch (SAXException e) {
            System.err.println(outputTextPrefix + "Parsing failure: " + e.getMessage());
        } catch (IOException e) {
            System.err.println(outputTextPrefix + "File error or I/O error: " + e.getMessage());
        }

        return departments;
    }

    private Department createDepartment(Element departmentElement) {
        Department department = new Department();

        department.setId(departmentElement.getAttribute("id"));
        department.setName(departmentElement.getAttribute("name"));

        return department;
    }

    private Employee createEmployee(Element employeeElement) {
        Employee employee = new Employee();

        employee.setId(employeeElement.getAttribute("id"));

        employee.setForename(getElementTextContent(employeeElement, "forename"));
        employee.setSurname(getElementTextContent(employeeElement, "surname"));
        employee.setSalary(new BigInteger(getElementTextContent(employeeElement, "salary")));

        return employee;
    }

    private static String getElementTextContent(Element element, String descendantElementTagName) {
        NodeList descendantElements = element.getElementsByTagName(descendantElementTagName);
        if (descendantElements.getLength() == 0) {
            throw new IllegalArgumentException(outputTextPrefix + "The element '" + element.getTagName() + "' does not have a descendant element '" + descendantElementTagName +"'.");
        } else {
            String textContent = descendantElements.item(0).getTextContent();
            return (textContent == null) ? "" : textContent;
        }
    }

    public Document createXmlDocument(Set<Department> departments) {
        Document document = documentBuilder.newDocument();

        Element rootElement = document.createElement("departments");
        document.appendChild(rootElement);

        for (Department department : departments) {
            Element departmentElement = document.createElement("department");
            departmentElement.setAttribute("id", department.getId());
            departmentElement.setAttribute("name", department.getName());

            for (Employee employee : department.getEmployee()) {
                Element employeeElement = document.createElement("employee");
                employeeElement.setAttribute("id", employee.getId());

                Element forenameElement = document.createElement("forename");
                forenameElement.appendChild(document.createTextNode(employee.getForename()));

                Element surnameElement = document.createElement("surname");
                surnameElement.appendChild(document.createTextNode(employee.getSurname()));

                Element salaryElement = document.createElement("salary");
                salaryElement.appendChild(document.createTextNode(employee.getSalary().toString()));

                employeeElement.appendChild(forenameElement);
                employeeElement.appendChild(surnameElement);
                employeeElement.appendChild(salaryElement);

                departmentElement.appendChild(employeeElement);
            }

            rootElement.appendChild(departmentElement);
        }

        return document;
    }
}
