package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/devdbtest";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static Connection connection;
    private static SessionFactory sessionFactory = new Configuration()
                .addAnnotatedClass(User.class)
                .setProperty(AvailableSettings.URL, URL)
                .setProperty(AvailableSettings.USER, USERNAME)
                .setProperty(AvailableSettings.PASS, PASSWORD)
                .setProperty(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, "thread")
                .setProperty(AvailableSettings.SHOW_SQL, "true")
                .setProperty(AvailableSettings.FORMAT_SQL, "true")
                .setProperty(AvailableSettings.HIGHLIGHT_SQL, "true")
                .buildSessionFactory();

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                    connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при подключении к БД");
        }

        return connection;
    }

    public static Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
