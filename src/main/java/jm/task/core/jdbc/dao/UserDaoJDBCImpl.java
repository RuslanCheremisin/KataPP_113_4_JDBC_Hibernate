package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try(Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            String createTable = """
            CREATE TABLE IF NOT EXISTS users (
                id BIGINT PRIMARY KEY AUTO_INCREMENT,
                name VARCHAR(50) NOT NULL,
                last_name VARCHAR(50) NOT NULL,
                age INT NOT NULL
            )
            """;
            statement.execute(createTable);
            System.out.println("Таблица users создана (или уже существовала)");
        } catch (SQLException e) {
            System.err.println("Ошибка при создании таблицы: " + e.getMessage());
        }
    }

    public void dropUsersTable() {
        try(Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            String dropTable = """
            DROP TABLE IF EXISTS users
            """;
            statement.execute(dropTable);
            System.out.println("Таблица users удалена (если существовала)");
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении таблицы: " + e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String saveUser = "INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)";

        try(Connection connection = Util.getConnection(); PreparedStatement statement = connection.prepareStatement(saveUser)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.println("User " + name + " " + lastName + " is added to DB");
        } catch (SQLException e) {
            System.out.println("ERROR! User " + name + " " + lastName + " is NOT added to DB");
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String removeUserById = "DELETE FROM users WHERE userid=" + id;
        try(Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(removeUserById);
            System.out.println("User " + id + " is removed from DB");
        } catch (SQLException e) {
            System.out.println("ERROR! User " + id + " is NOT removed from DB");
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {

        List<User> userList = new ArrayList<>();
        String getAllUsers = "SELECT * FROM users";
        try(Connection connection = Util.getConnection(); PreparedStatement statement = connection.prepareStatement(getAllUsers)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                userList.add(new User(resultSet.getString(2), resultSet.getString(3), resultSet.getByte(4)));
            }
            System.out.println("All users from DB are provided in the list");

        } catch (SQLException e) {
            System.out.println("ERROR! Cannot get users from DB");
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        String cleanTableUsers = "TRUNCATE TABLE users";
        try(Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(cleanTableUsers);
            System.out.println("All users from DB are removed. ID increment is reset");

        } catch (SQLException e) {
            System.out.println("ERROR! Cannot remove users from DB");
            e.printStackTrace();
        }
    }
}
