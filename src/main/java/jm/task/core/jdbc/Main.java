package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = Util.getConnection()) {
            System.out.println("Успешное подключение к MySQL!");
        } catch (SQLException e) {
            System.err.println("Ошибка подключения: " + e.getMessage());
        }

        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Vasya", "Poopkeen", (byte) 24);
        userService.saveUser("Kolya", "Chudo", (byte) 25);
        userService.saveUser("Vitya", "Solntse", (byte) 45);
        userService.saveUser("Eezya", "Bergberg", (byte) 60);

        userService.getAllUsers().stream().forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();


        // реализуйте алгоритм здесь
    }
}
