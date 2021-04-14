package ua.knu.csc.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Manager {
    private Connection connection;
    private Statement statement;

    public Manager(String url, String user, String password) throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
        statement = connection.createStatement();
    }

    public void stop() throws SQLException {
        connection.close();
    }

    public void addDepartment(String id, String name) throws SQLException {
        String sql = "INSERT INTO department VALUES" +
                "('" + id + "', '" + name + "');";
        statement.executeUpdate(sql);
    }
}
