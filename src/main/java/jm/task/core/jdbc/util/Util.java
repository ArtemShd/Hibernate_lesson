package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/mybdtest";
    private static final String USER = "root";
    private static final String PASSWORD = "Temka2323";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Подключение к БД установлено");
        } catch (SQLException e) {
            System.out.println("Подключение к БД НЕ установлено");
        }
        return connection;
    }
}
