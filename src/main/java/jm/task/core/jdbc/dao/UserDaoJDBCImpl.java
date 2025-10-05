package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;

public class UserDaoJDBCImpl extends Util implements UserDao {
    private static final String CREATE_USERS_TABLE_SQL = "CREATE TABLE IF NOT EXISTS users(" +
            "ID BIGINT NOT NULL AUTO_INCREMENT, NAME VARCHAR(100), " +
            "LASTNAME VARCHAR(100), AGE INT, PRIMARY KEY (ID))";

    private static final String DROP_USERS_TABLE_SQL = "DROP TABLE IF EXISTS users";

    private static final String INSERT_USER_SQL = "INSERT INTO users (NAME, LASTNAME, AGE) VALUES (?, ?, ?)";

    private static final String DELETE_USER_BY_ID_SQL = "DELETE FROM users WHERE ID=?";

    private static final String SELECT_ALL_USERS_SQL = "SELECT ID, NAME, LASTNAME, AGE FROM users";

    private static final String CLEAN_USERS_TABLE_SQL = "DELETE FROM users";

    private Connection connection = getConnection();

    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Statement stat = connection.createStatement()) {
            stat.executeUpdate(CREATE_USERS_TABLE_SQL);
            System.out.println("Таблица создана");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Statement stat = connection.createStatement()) {
            stat.executeUpdate(DROP_USERS_TABLE_SQL);
            System.out.println("Добавлено в таблицу");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preStat = connection.prepareStatement(INSERT_USER_SQL)) {
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
        try (PreparedStatement preStat = connection.prepareStatement(DELETE_USER_BY_ID_SQL)) {
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
        try (Statement stat = connection.createStatement()) {
            ResultSet resultSet = stat.executeQuery(SELECT_ALL_USERS_SQL);
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
        try (Statement stat = connection.createStatement()) {
            stat.executeUpdate(CLEAN_USERS_TABLE_SQL);
            System.out.println("Таблица очищена");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
