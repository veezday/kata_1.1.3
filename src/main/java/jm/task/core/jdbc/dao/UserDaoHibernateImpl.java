package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "  id INT UNSIGNED NOT NULL AUTO_INCREMENT," +
                "  name TINYTEXT NOT NULL," +
                "  last_name TINYTEXT NOT NULL," +
                "  age TINYINT(127) UNSIGNED NOT NULL," +
                "  PRIMARY KEY (id)," +
                "  UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE);";
        Session session = Util.getSession();

        session.beginTransaction();
        session.createNativeQuery(sql).executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        Session session = Util.getSession();

        session.beginTransaction();
        session.createNativeQuery(sql).executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, last_name, age) VALUES (:name, :last_name, :age)";
        Session session = Util.getSession();

        session.beginTransaction();
        session.createNativeQuery(sql)
                .setParameter("name", name)
                .setParameter("last_name", lastName)
                .setParameter("age", age)
                .executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = :id";
        Session session = Util.getSession();

        session.beginTransaction();
        session.createNativeQuery(sql)
                .setParameter("id", id)
                .executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        Session session = Util.getSession();

        session.beginTransaction();
        List<User> users = session.createNativeQuery(sql, User.class).list();
        session.getTransaction().commit();

        return users;
    }

    @Override
    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users";
        Session session = Util.getSession();

        session.beginTransaction();
        session.createNativeQuery(sql).executeUpdate();
        session.getTransaction().commit();
    }
}
