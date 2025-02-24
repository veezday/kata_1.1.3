package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl service = new UserServiceImpl();

        service.createUsersTable();
        service.saveUser("Abbe", "Doe", (byte) 19);
        service.saveUser("John", "Doe", (byte) 36);
        service.saveUser("Gordon", "Freeman", (byte) 42);
        service.saveUser("Mike", "Wazovskie", (byte) 24);
        System.out.println(service.getAllUsers());
        service.cleanUsersTable();
        service.dropUsersTable();
    }
}
