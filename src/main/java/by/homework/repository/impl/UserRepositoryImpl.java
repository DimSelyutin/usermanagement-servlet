package by.homework.repository.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import by.homework.config.ConfigHibernate;
import by.homework.entity.User;
import by.homework.exception.DaoException;
import by.homework.repository.UserRepository;

public class UserRepositoryImpl implements UserRepository {
    private static UserRepository instance = null;

    private UserRepositoryImpl() {

    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepositoryImpl();
        }
        return instance;
    }

    // Create or insert user
    public void createUser(User user) {
        Transaction transaction = null;
        try (Session session = ConfigHibernate.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException("Error occurred while inserting USER", e);

        }
    }

    // Find user by ID
    public User findUserById(Long id) {
        User user = null;
        try (Session session = ConfigHibernate.getSessionFactory().openSession()) {
            user = session.get(User.class, id);
        } catch (Exception e) {
            throw new DaoException("Error occurred while getting user by ID", e);

        }
        return user;
    }

    public List<User> findAllUsers() {
        List<User> users = null;
        try (Session session = ConfigHibernate.getSessionFactory().openSession()) {
            // Создание HQL запроса для получения всех пользователей
            String hql = "FROM User";
            Query<User> query = session.createQuery(hql, User.class);
            users = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    // Delete user by ID
    public boolean deleteUser(Long id) {
        Transaction transaction = null;
        boolean isDeleted = false;
        try (Session session = ConfigHibernate.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
                isDeleted = true;
            } else {
                throw new DaoException("User with id " + id + " not found.");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            isDeleted = false;
        }
        return isDeleted;
    }

    // Update user
    public boolean updateUser(User user) {
        Transaction transaction = null;
        boolean isUpdated = false;
        try (Session session = ConfigHibernate.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
            isUpdated = true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            isUpdated = false;
        }
        return isUpdated;
    }
}