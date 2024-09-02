package by.homework.repository.impl;

import java.util.List;

import javax.management.relation.RoleNotFoundException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import by.homework.entity.Role;
import by.homework.entity.User;
import by.homework.exception.DaoException;
import by.homework.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {
    private final SessionFactory sessionFactory;

    // Create or insert user
    public void saveUser(User user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException("Error occurred while inserting USER {}", e);

        }
    }

    // Find user by ID
    public User findUserById(Long id) {
        User user = null;
        try (Session session = sessionFactory.openSession()) {
            user = session.get(User.class, id);
        } catch (Exception e) {
            throw new DaoException("Error occurred while getting user by ID", e);

        }
        return user;
    }

    public List<User> findAllUsers() {
        List<User> users = null;
        try (Session session = sessionFactory.openSession()) {
            // Использование LEFT JOIN FETCH для немедленной загрузки ролей
            String hql = "SELECT u FROM User u LEFT JOIN FETCH u.roles";
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
        try (Session session = sessionFactory.openSession()) {
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
        try (Session session = sessionFactory.openSession()) {
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

    // использовал JOIN FETCH для устранения LazyInitEx
    @Override
    public void assignRoleToUser(Long userId, Long roleId) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Role role = session.get(Role.class, roleId);
            if (role == null) {
                throw new RoleNotFoundException(String.format("Role not found with id:%d", roleId));
            }
            // Загрузка пользователя вместе с его ролями
            User user = session.createQuery("FROM User u JOIN FETCH u.roles WHERE u.id = :userId", User.class)
                    .setParameter("userId", userId)
                    .uniqueResult();

            if (user != null) {
                user.getRoles().add(role);
                session.update(user); // Обновление пользователя с новой ролью
            }

            transaction.commit();
        } catch (Exception e) {
            log.info("user: {}", e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException("Error occurred while assigning role to user", e);
        }
    }
}