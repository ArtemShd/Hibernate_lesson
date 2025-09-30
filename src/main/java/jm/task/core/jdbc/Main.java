package jm.task.core.jdbc;
import java.util.ArrayList;
import java.util.List;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.*;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        List<User> users = new ArrayList<>();
        users.add(new User("Petr", "Petrov", (byte) 20));
        users.add(new User("Ivan", "Ivanov", (byte) 30));
        users.add(new User("Vladimir", "Vladimirov", (byte) 35));
        users.add(new User("Egor", "Egorov", (byte) 40));
        for (User us : users) {
            userService.saveUser(us.getName(), us.getLastName(), (byte) us.getAge());
            System.out.println("User с именем – " + us.getName() + " добавлен в БД");
        }
        List<User> usersTable = userService.getAllUsers();
        for (User us : usersTable) {
            System.out.println(us);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
