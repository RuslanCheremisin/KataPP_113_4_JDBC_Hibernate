package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
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
