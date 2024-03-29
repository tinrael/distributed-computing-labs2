package ua.knu.csc.core;

import ua.knu.csc.entity.Department;
import ua.knu.csc.entity.Employee;

import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataAccessObject { // Data Access Object (DAO)
    private final Connection connection;
    private final Statement statement;

    public DataAccessObject(String url, String user, String password) throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
        statement = connection.createStatement();
    }

    public synchronized void stop() throws SQLException {
        connection.close();
    }

    public synchronized boolean addDepartment(String id, String name) {
        String sql = "INSERT INTO department VALUES " +
                "('" + id + "', '" + name + "');";

        try {
            statement.executeUpdate(sql);

            System.out.println("[SUCCESS]: The " + name + " department successfully added.");
            return true;
        } catch (SQLException throwable) {
            System.err.println("[FAIL]: Unable to add the " + name + " department.");

            throwable.printStackTrace();

            return false;
        }
    }

    public synchronized boolean updateDepartment(String id, String name) {
        String sql = "UPDATE department " +
                "SET name = '" + name + "' " +
                "WHERE department_id = '" + id + "';";

        try {
            int rowsAffectedCount = statement.executeUpdate(sql);

            if (rowsAffectedCount > 0) {
                System.out.println("[SUCCESS]: The department with the identifier '" + id + "' successfully updated.");
                return true;
            } else {
                System.err.println("[FAIL]: The department with the identifier '" + id + "' not found.");
                return false;
            }
        } catch (SQLException throwable) {
            System.err.println("[FAIL]: Unable to update the department with the identifier '" + id + "'.");

            throwable.printStackTrace();

            return false;
        }
    }

    public synchronized Department getDepartment(String departmentId) {
        String sql = "SELECT name " +
                "FROM department " +
                "WHERE department_id = '" + departmentId + "';";

        try (Statement otherStatement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                Department department = new Department();

                department.setDepartmentId(departmentId);
                department.setName(resultSet.getString("name"));

                String query = "SELECT employee_id " +
                        "FROM employee " +
                        "WHERE department_id = '" + departmentId + "';";

                ResultSet employeeIdsResultSet = otherStatement.executeQuery(query);

                while (employeeIdsResultSet.next()) {
                    Employee employee = getEmployee(employeeIdsResultSet.getString("employee_id"));

                    if (employee == null) {
                        System.err.println("[FAIL]: Unable to get the department with the identifier '" + departmentId + "' from the database.");
                        return null;
                    }

                    department.getEmployees().add(employee);
                }

                System.out.println("[SUCCESS]: The department with the identifier '" + departmentId + "' successfully received from the database.");

                return department;
            } else {
                System.err.println("[FAIL]: The department with the identifier '" + departmentId + "' not found.");
                return null;
            }
        } catch (SQLException throwable) {
            System.err.println("[FAIL]: Unable to get the department with the identifier '" + departmentId + "' from the database.");

            throwable.printStackTrace();

            return null;
        }
    }

    public synchronized boolean deleteDepartment(String id) {
        String sql = "DELETE FROM department WHERE department_id = '" + id + "';";

        try {
            int rowsAffectedCount = statement.executeUpdate(sql);

            if (rowsAffectedCount > 0) {
                System.out.println("[SUCCESS]: The department with the identifier '" + id + "' successfully deleted.");
                return true;
            } else {
                System.err.println("[FAIL]: The department with the identifier '" + id + "' not found.");
                return false;
            }
        } catch (SQLException throwable) {
            System.err.println("[FAIL]: Unable to delete the department with the identifier '" + id + "'.");

            throwable.printStackTrace();

            return false;
        }
    }

    public synchronized List<String> getAllDepartmentIds() {
        List<String> allDepartmentIds = new ArrayList<>();

        String sql = "SELECT department_id FROM department;";

        try {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                allDepartmentIds.add(resultSet.getString("department_id"));
            }

            System.out.println("[SUCCESS]: The department identifiers successfully received from the database.");
        } catch (SQLException throwable) {
            System.err.println("[FAIL]: Unable to get the department identifiers from the database.");
            throwable.printStackTrace();
        }

        return allDepartmentIds;
    }

    public synchronized boolean addEmployee(String employeeId, String forename, String surname, long salary, String departmentId) {
        String sql = "INSERT INTO employee VALUES " +
                "('" + employeeId + "', '" + forename +
                "', '" + surname + "', " + salary + ", '" + departmentId + "');";

        try {
            statement.executeUpdate(sql);

            System.out.println("[SUCCESS]: The employee [" + forename + " " + surname + "] successfully added.");
            return true;
        } catch (SQLException throwable) {
            System.err.println("[FAIL]: Unable to add the employee [" + forename + " " + surname + "].");

            throwable.printStackTrace();

            return false;
        }
    }

    public synchronized boolean updateEmployee(String employeeId, String forename, String surname, long salary, String departmentId) {
        String sql = "UPDATE employee " +
                "SET forename = '" + forename + "', surname = '" + surname + "', salary = " + salary + ", department_id = '" + departmentId + "' " +
                "WHERE employee_id = '" + employeeId + "';";

        try {
            int rowsAffectedCount = statement.executeUpdate(sql);

            if (rowsAffectedCount > 0) {
                System.out.println("[SUCCESS]: The employee with the identifier '" + employeeId + "' successfully updated.");
                return true;
            } else {
                System.err.println("[FAIL]: The employee with the identifier '" + employeeId + "' not found.");
                return false;
            }
        } catch (SQLException throwable) {
            System.err.println("[FAIL]: Unable to update the employee with the identifier '" + employeeId + "'.");

            throwable.printStackTrace();

            return false;
        }
    }

    public synchronized Employee getEmployee(String id) {
        String sql = "SELECT forename, surname, salary, department_id " +
                "FROM employee " +
                "WHERE employee_id = '" + id + "';";

        try {
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                Employee employee = new Employee();

                employee.setEmployeeId(id);
                employee.setForename(resultSet.getString("forename"));
                employee.setSurname(resultSet.getString("surname"));
                employee.setSalary(resultSet.getLong("salary"));
                employee.setDepartmentId(resultSet.getString("department_id"));

                System.out.println("[SUCCESS]: The employee with the identifier '" + id + "' successfully received from the database.");

                return employee;
            } else {
                System.err.println("[FAIL]: The employee with the identifier '" + id + "' not found.");
                return null;
            }
        } catch (SQLException throwable) {
            System.err.println("[FAIL]: Unable to get the employee with the identifier '" + id + "' from the database.");

            throwable.printStackTrace();

            return null;
        }
    }

    public synchronized boolean deleteEmployee(String id) {
        String sql = "DELETE FROM employee WHERE employee_id = '" + id + "';";

        try {
            int rowsAffectedCount = statement.executeUpdate(sql);

            if (rowsAffectedCount > 0) {
                System.out.println("[SUCCESS]: The employee with the identifier '" + id + "' successfully deleted.");
                return true;
            } else {
                System.err.println("[FAIL]: The employee with the identifier '" + id + "' not found.");
                return false;
            }
        } catch (SQLException throwable) {
            System.err.println("[FAIL]: Unable to delete the employee with the identifier '" + id + "'.");

            throwable.printStackTrace();

            return false;
        }
    }

    public synchronized List<String> getAllEmployeeIds() {
        List<String> allEmployeeIds = new ArrayList<>();

        String sql = "SELECT employee_id FROM employee;";

        try {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                allEmployeeIds.add(resultSet.getString("employee_id"));
            }

            System.out.println("[SUCCESS]: The employee identifiers successfully received from the database.");
        } catch (SQLException throwable) {
            System.err.println("[FAIL]: Unable to get the employee identifiers from the database.");
            throwable.printStackTrace();
        }

        return allEmployeeIds;
    }
}
