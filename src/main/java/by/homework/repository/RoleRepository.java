package by.homework.repository;

import java.util.List;

import by.homework.entity.Role;
import by.homework.exception.DaoException;
import by.homework.repository.RoleRepository;

public interface RoleRepository {

    public void saveRole(Role role) throws DaoException;

    public void saveRoles(List<Role> roles) throws DaoException;

    public List<Role> getRolesWithIdGreaterThan2000(int pageNumber, int pageSize);

    public void updateRole(Role role) throws DaoException;

    public Role findRoleById(Long id) throws DaoException;

    public void deleteRole(Long id) throws DaoException;

    public List<Role> findAllRoles() throws DaoException;
}
