package by.homework.service;

import java.util.List;

import by.homework.entity.Role;
import by.homework.exception.ServiceException;

public interface RoleService {

    public void insertRole(Role role) throws ServiceException;

    public Role getRoleById(int id) throws ServiceException;

    public List<Role> getAllRoles() throws ServiceException;

    public void updateRole(Role role) throws ServiceException;

    public void deleteRole(int id) throws ServiceException;
}
