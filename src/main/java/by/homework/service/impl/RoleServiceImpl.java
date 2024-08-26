package by.homework.service.impl;

import by.homework.entity.Role;
import by.homework.exception.DaoException;
import by.homework.exception.ServiceException;
import by.homework.repository.RoleRepository;
import by.homework.repository.impl.RoleDaoImpl;
import by.homework.service.RoleService;

import java.util.List;

public class RoleServiceImpl implements RoleService {
    private static RoleServiceImpl instance;
    private final RoleRepository roleRepository;

    private RoleServiceImpl() {
        roleRepository = RoleDaoImpl.getInstance();
    }

    public static RoleServiceImpl getInstance() {
        if (instance == null) {
            instance = new RoleServiceImpl();
        }
        return instance;
    }

    @Override
    public void saveRole(Role role) throws ServiceException {
        try {
            roleRepository.saveRole(role);
        } catch (DaoException e) {
            throw new ServiceException("Error occurred while inserting role");
        }
    }

    @Override
    public Role getRoleById(int id) throws ServiceException {
        try {
            return roleRepository.getRoleById(id);
        } catch (DaoException e) {
            throw new ServiceException("Error occurred while getting role by ID");
        }
    }

    @Override
    public List<Role> getAllRoles() throws ServiceException {
        try {
            return roleRepository.findAllRoles();
        } catch (DaoException e) {
            throw new ServiceException("Error occurred while getting all roles");
        }
    }

    @Override
    public void updateRole(Role role) throws ServiceException {
        try {
            roleRepository.updateRole(role);
        } catch (DaoException e) {
            throw new ServiceException("Error occurred while updating role");
        }
    }

    @Override
    public void deleteRole(int id) throws ServiceException {
        try {
            roleRepository.deleteRole(id);
        } catch (DaoException e) {
            throw new ServiceException("Error occurred while deleting role");
        }
    }
}
