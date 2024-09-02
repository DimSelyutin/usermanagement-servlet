package by.homework.repository.impl;

import by.homework.entity.Role;
import by.homework.exception.DaoException;
import by.homework.repository.CustomRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class RoleRepositoryImpl implements CustomRoleRepository {

    // private final SessionFactory sessionFactory;

    @Override
    @Transactional(readOnly = true)
    public List<Role> getRolesWithIdGreaterThan2000(int pageNumber, int pageSize) {
        List<Long> roleIds = getRoleIdsWithIdGreaterThan2000(pageNumber, pageSize);
        return getRolesByIds(roleIds);
    }

    @Transactional(readOnly = true)
    public List<Long> getRoleIdsWithIdGreaterThan2000(int pageNumber, int pageSize) {
        // try (Session session = sessionFactory.openSession()) {
        // Query<Long> query = session.createQuery(
        // "SELECT r.id FROM Role r WHERE r.id > 2000", Long.class);
        // query.setFirstResult((pageNumber - 1) * pageSize);
        // query.setMaxResults(pageSize);

        // return query.list();
        // } catch (Exception e) {
        // log.error("Error occurred while fetching role IDs with ID greater than 2000",
        // e);
        // throw new DaoException("An error occurred while fetching role IDs.", e);
        // }
        return null;
    }

    @Transactional(readOnly = true)
    public List<Role> getRolesByIds(List<Long> roleIds) {
        // if (roleIds == null || roleIds.isEmpty()) {
        // return Collections.emptyList();
        // }

        // try (Session session = sessionFactory.openSession()) {
        // Query<Role> query = session.createQuery(
        // "SELECT DISTINCT r FROM Role r LEFT JOIN FETCH r.users WHERE r.id IN (:ids)",
        // Role.class);
        // query.setParameter("ids", roleIds);

        // return query.list();
        // } catch (Exception e) {
        // log.error("Error occurred while fetching roles by IDs", e);
        // throw new DaoException("An error occurred while fetching roles.", e);
        // }
        return null;
    }
}