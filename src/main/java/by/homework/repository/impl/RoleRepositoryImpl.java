package by.homework.repository.impl;

import by.homework.config.ConfigHibernate;
import by.homework.entity.Role;
import by.homework.entity.User;
import by.homework.exception.DaoException;
import by.homework.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Collections;
import java.util.List;

@Slf4j
public class RoleRepositoryImpl implements RoleRepository {

    private static RoleRepositoryImpl instance;

    private RoleRepositoryImpl() {
    }

    public static RoleRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new RoleRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void saveRole(Role role) throws DaoException {
        Transaction transaction = null;
        try (Session session = ConfigHibernate.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(role);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            throw new DaoException("Error occurred while inserting role", e);
        }
    }

    @Override
    public void saveRoles(List<Role> roles) throws DaoException {
        Transaction transaction = null;
        try (Session session = ConfigHibernate.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            for (Role role : roles) {
                session.save(role);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException("Error occurred while inserting roles", e);
        }
    }

    @Override
    public Role findRoleById(Long id) throws DaoException {
        try (Session session = ConfigHibernate.getSessionFactory().openSession()) {
            return session.get(Role.class, id);
        } catch (Exception e) {
            throw new DaoException("Error occurred while getting role by ID", e);
        }
    }

    @Override
    public void deleteRole(Long id) throws DaoException {
        Transaction transaction = null;
        try (Session session = ConfigHibernate.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Role role = session.get(Role.class, id);
            if (role != null) {
                session.delete(role);
                transaction.commit();
            } else {
                throw new DaoException("Role with id " + id + " not found.");
            }
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            throw new DaoException("Error occurred while deleting role", e);
        }
    }

    public List<Role> findAllRoles() {
        List<Role> roles = null;
        try (Session session = ConfigHibernate.getSessionFactory().openSession()) {
            // Использование LEFT JOIN FETCH для немедленной загрузки ролей
            String hql = "SELECT r FROM Role r LEFT JOIN FETCH r.users";
            Query<Role> query = session.createQuery(hql, Role.class);
            roles = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roles;
    }

    @Override
    public void updateRole(Role role) throws DaoException {
        Transaction transaction = null;
        try (Session session = ConfigHibernate.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(role);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            throw new DaoException("Error occurred while updating role", e);
        }
    }

    // Получение записей, где id > 2000
    @Override
    public List<Role> getRolesWithIdGreaterThan2000(int pageNumber, int pageSize) {
        //вызов методов получения id и ролей по этм id
        List<Long> roleIds = getRoleIdsWithIdGreaterThan2000(pageNumber, pageSize);
        return getRolesByIds(roleIds);
    }

    // получение id
    public List<Long> getRoleIdsWithIdGreaterThan2000(int pageNumber, int pageSize) {
        try (Session session = ConfigHibernate.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                    "SELECT r.id FROM Role r WHERE r.id > 2000", Long.class);
            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);

            return query.list();
        }
    }

    // получение ролей
    public List<Role> getRolesByIds(List<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return Collections.emptyList();
        }

        try (Session session = ConfigHibernate.getSessionFactory().openSession()) {
            Query<Role> query = session.createQuery(
                    "SELECT DISTINCT r FROM Role r LEFT JOIN FETCH r.users WHERE r.id IN (:ids)", Role.class);
            query.setParameter("ids", roleIds);

            return query.list();
        }
    }
}