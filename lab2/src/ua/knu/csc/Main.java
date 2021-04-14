package ua.knu.csc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost/departments";
        String user = "postgres";
        String password = "postgres";
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
