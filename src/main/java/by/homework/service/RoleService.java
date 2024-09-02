package by.homework.service;

import java.util.List;
import java.util.Optional;

import by.homework.entity.Role;

public interface RoleService {

    public void saveRole(Role role);

    public Optional<Role> findRoleById(Long id) ;

    public List<Role> getAllRoles() ;

    public List<Role> getRolesWithIdGreaterThan2000(int pageNumber, int pageSize);

    public void updateRole(Role role) ;

    public void deleteRole(Long id) ;

    public void saveRoles(List<Role> roles);
}
