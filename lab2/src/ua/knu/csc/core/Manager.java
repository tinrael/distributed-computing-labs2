package ua.knu.csc.core;

import java.util.ArrayList;
import java.util.List;

import java.math.BigInteger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Manager { // DAO (Data Access Object)
    private final Connection connection;
    private final Statement statement;

    public Manager(String url, String user, String password) throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
        statement = connection.createStatement();
    }

    public void stop() throws SQLException {
        connection.close();
    }

    public boolean addDepartment(String id, String name) {
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

    public boolean updateDepartment(String id, String name) {
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

    public boolean deleteDepartment(String id) {
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

    public boolean addEmployee(String employeeId, String forename, String surname, BigInteger salary, String departmentId) {
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

    public boolean updateEmployee(String employeeId, String forename, String surname, BigInteger salary, String departmentId) {
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

    public boolean deleteEmployee(String id) {
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

    public List<String> getAllEmployeeIds() {
        List<String> allEmployeeIds = new ArrayList<>();

        String sql = "SELECT employee_id FROM employee;";

        try {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                allEmployeeIds.add(resultSet.getString("employee_id"));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return allEmployeeIds;
    }
}
