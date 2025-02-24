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
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "  id INT UNSIGNED NOT NULL AUTO_INCREMENT," +
                "  name TINYTEXT NOT NULL," +
                "  last_name TINYTEXT NOT NULL," +
                "  age TINYINT(127) UNSIGNED NOT NULL," +
                "  PRIMARY KEY (id)," +
                "  UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE);";

        try (Connection connection = Util.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка создания таблицы " + e.getMessage());
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";

        try (Connection connection = Util.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка удаления таблицы " + e.getMessage());;
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)";

        try (Connection connection = Util.getConnection()) {
            if (tableExists(connection, "users")) {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, name);
                statement.setString(2, lastName);
                statement.setInt(3, age);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Ошибка добавления записи " + e.getMessage());
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection connection = Util.getConnection()) {
            if (tableExists(connection, "users")) {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setLong(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Ошибка удаления записи " + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        ArrayList<User> users = new ArrayList<>();

        try (Connection connection = Util.getConnection()) {
            if (tableExists(connection, "users")) {
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    String lastName = resultSet.getString("last_name");
                    byte age = resultSet.getByte("age");

                    User user = new User(name, lastName, age);
                    user.setId(id);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            System.out.println("Ошибка получения таблицы " + e.getMessage());
        }

        return users;
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users";

        try (Connection connection = Util.getConnection()) {
            if (tableExists(connection, "users")) {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Ошибка очистки таблицы " + e.getMessage());
        }
    }

    private static boolean tableExists(Connection connection, String name) throws SQLException {
        return connection.getMetaData().getTables(null, null, name, new String[]{"TABLE"}).next();
    }
}
