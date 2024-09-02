package by.homework.repository;

import java.util.List;

import by.homework.entity.Role;

public interface CustomRoleRepository{
    public List<Role> getRolesWithIdGreaterThan2000(int pageNumber, int pageSize);

    public List<Long> getRoleIdsWithIdGreaterThan2000(int pageNumber, int pageSize);
}
