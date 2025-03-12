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
        User user = new User(name, lastName, age);
        user.setId(getNextId());
        Session session = Util.getSession();

        session.beginTransaction();
        session.save(user);
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
        String hql = "select u from User u";
        Session session = Util.getSession();

        session.beginTransaction();
        List<User> users = session.createQuery(hql, User.class).getResultList();
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

    private Long getNextId() {
        String hql = "select MAX(u.id) from User u";
        Session session = Util.getSession();

        session.beginTransaction();
        Long id = (Long) session.createQuery(hql).getSingleResult();
        session.getTransaction().commit();

        return (id != null) ? ++id : 0;
    }
}
