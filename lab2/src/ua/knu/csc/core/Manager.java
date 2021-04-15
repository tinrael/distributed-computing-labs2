package ua.knu.csc.core;

import java.math.BigInteger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Manager {
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
}
