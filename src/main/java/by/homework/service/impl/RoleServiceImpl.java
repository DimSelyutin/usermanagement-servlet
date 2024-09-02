package by.homework.service.impl;

import by.homework.entity.Role;
import by.homework.exception.DaoException;
import by.homework.exception.ServiceException;
import by.homework.repository.RoleRepository;
import by.homework.service.RoleService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void saveRole(Role role) throws ServiceException {
        try {
            roleRepository.saveRole(role);
        } catch (DaoException e) {
            throw new ServiceException("Error occurred while inserting role");
        }
    }

    @Override
    public void saveRoles(List<Role> roles) throws ServiceException {
        try {
            roleRepository.saveRoles(roles);
        } catch (DaoException e) {
            throw new ServiceException("Error occurred while inserting roles");
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
    public List<Role> getRolesWithIdGreaterThan2000(int pageNumber, int pageSize) throws ServiceException {
        try {
            return roleRepository.getRolesWithIdGreaterThan2000(pageNumber, pageSize);
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
