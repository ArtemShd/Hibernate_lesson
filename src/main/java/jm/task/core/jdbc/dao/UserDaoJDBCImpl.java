package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;

public class UserDaoJDBCImpl extends Util implements UserDao {
    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users(" +
                "ID BIGINT NOT NULL AUTO_INCREMENT, NAME VARCHAR(100), " +
                "LASTNAME VARCHAR(100), AGE INT, PRIMARY KEY (ID) )";
        try (Connection connection = getConnection();
             Statement stat = connection.createStatement()) {
            stat.executeUpdate(sql);
            System.out.println("Таблица создана");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        try (Connection connection = getConnection();
             Statement stat = connection.createStatement()) {
            stat.executeUpdate(sql);
            System.out.println("Добавлено в таблицу");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (NAME, LASTNAME, AGE) VALUES(?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preStat = connection.prepareStatement(sql)) {
            preStat.setString(1, name);
            preStat.setString(2, lastName);
            preStat.setByte(3, age);
            preStat.executeUpdate();
            System.out.println("Пользователь добавлен");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE ID=?";
        try (Connection connection = getConnection();
             PreparedStatement preStat = connection.prepareStatement(sql)) {
            preStat.setLong(1, id);
            preStat.executeUpdate();
            System.out.println("Пользователь удален");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT ID, NAME, LASTNAME, AGE FROM users";
        try (Connection connection = getConnection();
             Statement stat = connection.createStatement()) {
            ResultSet resultSet = stat.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));
                userList.add(user);
            }
            System.out.println("Список пользователей");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        String sql = "DELETE FROM users";
        try (Connection connection = getConnection();
             Statement stat = connection.createStatement()) {
            stat.executeUpdate(sql);
            System.out.println("Таблица очищена");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
