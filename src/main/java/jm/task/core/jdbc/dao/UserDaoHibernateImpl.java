package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.UtilHiber;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {


    private static final String CLEAN_TABLE_USERS = "TRUNCATE TABLE users";
    private static final String DELETE_BY_ID = "DELETE FROM users WHERE id = ";
    private static final String INSERT_USERS = "INSERT INTO users (name, lastName, age) VALUE (?,?,?)";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS users";
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS users (" +
            " id INTEGER PRIMARY KEY NOT NULL" +
            " AUTO_INCREMENT," +
            " name VARCHAR(40), " +
            "lastName VARCHAR(40), " +
            "age INTEGER(3))";


    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Session session = UtilHiber.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(CREATE_TABLE).addEntity(User.class).executeUpdate();
            transaction.commit();
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = UtilHiber.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(DROP_TABLE).addEntity(User.class).executeUpdate();
            transaction.commit();
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try (Session session = UtilHiber.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = UtilHiber.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(DELETE_BY_ID + id).addEntity(User.class).executeUpdate();
            transaction.commit();
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = UtilHiber.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder().createQuery(User.class);
            criteriaQuery.from(User.class);
            List<User> result = session.createQuery(criteriaQuery).getResultList();
            transaction.commit();
            session.close();

            return result;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = UtilHiber.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(CLEAN_TABLE_USERS).addEntity(User.class).executeUpdate();
            transaction.commit();
            session.close();
        }
    }
}
