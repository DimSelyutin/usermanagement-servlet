package by.homework.service.impl;

import by.homework.entity.Role;
import by.homework.entity.User;
import by.homework.exception.DaoException;
import by.homework.exception.ServiceException;
import by.homework.repository.RoleRepository;
import by.homework.repository.UserRepository;
import by.homework.repository.impl.RoleRepositoryImpl;
import by.homework.repository.impl.UserRepositoryImpl;
import by.homework.service.RoleService;

import java.util.List;

import javax.transaction.Transactional;

public class RoleServiceImpl implements RoleService {
    private static RoleServiceImpl instance;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    private RoleServiceImpl() {
        roleRepository = RoleRepositoryImpl.getInstance();
        userRepository = UserRepositoryImpl.getInstance();
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
    public Role findRoleById(Long id) throws ServiceException {
        try {
            return roleRepository.findRoleById(id);
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
    public void deleteRole(Long id) throws ServiceException {
        try {
            roleRepository.deleteRole(id);
        } catch (DaoException e) {
            throw new ServiceException("Error occurred while deleting role");
        }
    }

}
