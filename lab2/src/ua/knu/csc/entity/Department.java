package ua.knu.csc.entity;

import java.util.ArrayList;
import java.util.List;

public class Department {
    private final List<Employee> employees = new ArrayList<>();

    private String departmentId;
    private String name;

    public List<Employee> getEmployees() {
        return employees;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
