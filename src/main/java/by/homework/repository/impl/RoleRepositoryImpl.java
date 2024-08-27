package by.homework.repository.impl;

import by.homework.config.ConfigHibernate;
import by.homework.entity.Role;
import by.homework.exception.DaoException;
import by.homework.repository.RoleRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

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

    @Override
    public List<Role> findAllRoles() throws DaoException {
        try (Session session = ConfigHibernate.getSessionFactory().openSession()) {
            return session.createQuery("from Role", Role.class).list();
        } catch (Exception e) {
            throw new DaoException("Error occurred while retrieving roles", e);
        }
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
}