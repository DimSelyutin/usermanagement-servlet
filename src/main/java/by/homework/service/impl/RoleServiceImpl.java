package by.homework.service.impl;

import by.homework.entity.Role;
import by.homework.exception.DaoException;
import by.homework.exception.ServiceException;
import by.homework.repository.RoleRepository;
import by.homework.service.RoleService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public void saveRole(Role role) {
        try {
            roleRepository.save(role);
        } catch (DaoException e) {
            throw new ServiceException("Error occurred while inserting role");
        }
    }

    @Override
    public void saveRoles(List<Role> roles) {
        try {
            roleRepository.saveAll(roles);
        } catch (DaoException e) {
            throw new ServiceException("Error occurred while inserting roles");
        }
    }

    @Override
    public Optional<Role> findRoleById(Long id){
        try {
            return roleRepository.findById(id);
        } catch (DaoException e) {
            throw new ServiceException("Error occurred while getting role by ID");
        }
    }

    @Override
    public List<Role> getAllRoles(){
        try {
            return roleRepository.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Error occurred while getting all roles");
        }
    }

    @Override
    public List<Role> getRolesWithIdGreaterThan2000(int pageNumber, int pageSize)  {
        try {
            return roleRepository.getRolesWithIdGreaterThan2000(pageNumber, pageSize);
        } catch (DaoException e) {
            throw new ServiceException("Error occurred while getting all roles");
        }
    }

    @Override
    public void updateRole(Role role) {
        try {
            roleRepository.findById(role.getId());
            roleRepository.save(role);
        } catch (DaoException e) {
            throw new ServiceException("Error occurred while updating role");
        }
    }

    @Override
    public void deleteRole(Long id) {
        try {
            roleRepository.deleteById(id);
        } catch (DaoException e) {
            throw new ServiceException("Error occurred while deleting role");
        }
    }

}
