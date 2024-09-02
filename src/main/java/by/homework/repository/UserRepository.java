package by.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import by.homework.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, CustomUserRepository {

}
