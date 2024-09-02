package by.homework.repository.impl;

import javax.management.relation.RoleNotFoundException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import by.homework.entity.Role;
import by.homework.entity.User;
import by.homework.exception.DaoException;
import by.homework.repository.CustomUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CustomUserRepositoryImpl implements CustomUserRepository {

    // private final SessionFactory sessionFactory;

    @Override
    public void assignRoleToUser(Long userId, Long roleId) {
        // Transaction transaction = null;
        // try (Session session = sessionFactory.openSession()) {
        //     transaction = session.beginTransaction();
        //     Role role = session.get(Role.class, roleId);
        //     if (role == null) {
        //         throw new RoleNotFoundException(String.format("Role not found with id:%d", roleId));
        //     }
        //     // Загрузка пользователя вместе с его ролями
        //     User user = session.createQuery("FROM User u JOIN FETCH u.roles WHERE u.id = :userId", User.class)
        //             .setParameter("userId", userId)
        //             .uniqueResult();

        //     if (user != null) {
        //         user.getRoles().add(role);
        //         session.update(user); // Обновление пользователя с новой ролью
        //     }

        //     transaction.commit();
        // } catch (Exception e) {
        //     log.info("user: {}", e.getMessage());
        //     if (transaction != null) {
        //         transaction.rollback();
        //     }
        //     throw new DaoException("Error occurred while assigning role to user", e);
        // }
    }
}