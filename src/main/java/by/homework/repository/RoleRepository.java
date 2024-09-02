package by.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import by.homework.entity.Role;
import by.homework.repository.RoleRepository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, CustomRoleRepository {

}
