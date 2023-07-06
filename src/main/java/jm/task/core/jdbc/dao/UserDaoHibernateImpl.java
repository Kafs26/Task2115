package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


import java.util.List;

public class UserDaoHibernateImpl extends Util implements UserDao {
    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            String sql = """
                    CREATE TABLE IF NOT EXISTS USERS
                    (id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(50) NOT NULL,
                    lastName VARCHAR(50) NOT NULL,
                    age TINYINT NOT NULL)
                    """;
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
            session.close();
        }

//        try (Session session = getSessionFactory().openSession()) {
//            session.beginTransaction();
//            session.getTransaction().commit();
//            session.close();
//        }
//    }
    }

    @Override
    public void dropUsersTable() {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String sql = "DROP TABLE IF EXISTS users";
        Query query = session.createSQLQuery(sql).addEntity(User.class);
        query.executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = getSessionFactory().openSession()) {
            Transaction tx1 = session.beginTransaction();
            session.save(new User(name, lastName, age));
            tx1.commit();
            session.close();
        } catch (Exception e) {

        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = getSessionFactory().openSession()) {
            Transaction tx1 = session.beginTransaction();
            User user = session.load(User.class, id);
            session.delete(user);
            tx1.commit();
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class).list();
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            String sql = "TRUNCATE USERS";
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
            session.close();
        }
    }
}
