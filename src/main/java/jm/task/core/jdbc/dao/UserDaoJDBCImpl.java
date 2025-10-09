package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;

public class UserDaoJDBCImpl implements UserDao {
    private static final String CREATE = "CREATE TABLE IF NOT EXISTS users(" +
            "ID BIGINT NOT NULL AUTO_INCREMENT, NAME VARCHAR(100), " +
            "LASTNAME VARCHAR(100), AGE INT, PRIMARY KEY (ID))";
    private static final String DROP = "DROP TABLE IF EXISTS users";
    private static final String INSERT = "INSERT INTO users (NAME, LASTNAME, AGE) VALUES (?, ?, ?)";
    private static final String DELETE = "DELETE FROM users WHERE ID=?";
    private static final String SELECT = "SELECT ID, NAME, LASTNAME, AGE FROM users";
    private static final String CLEAN = "DELETE FROM users";

    private Connection connection = new Util().getConnection();

    @Override
    public void createUsersTable() {
        try (Statement stat = connection.createStatement()) {
            stat.executeUpdate(CREATE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Statement stat = connection.createStatement()) {
            stat.executeUpdate(DROP);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preStat = connection.prepareStatement(INSERT)) {
            preStat.setString(1, name);
            preStat.setString(2, lastName);
            preStat.setByte(3, age);
            preStat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (PreparedStatement preStat = connection.prepareStatement(DELETE)) {
            preStat.setLong(1, id);
            preStat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Statement stat = connection.createStatement()) {
            ResultSet resultSet = stat.executeQuery(SELECT);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Statement stat = connection.createStatement()) {
            stat.executeUpdate(CLEAN);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
