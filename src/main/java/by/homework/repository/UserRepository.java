package by.homework.repository;

import java.util.List;

import by.homework.entity.User;

public interface UserRepository {

    public void createUser(User user);

    public User findUserById(Long id);

    // Select all users
    public List<User> findAllUsers();

    // Delete user by id
    public boolean deleteUser(Long id);

    // Update user
    public boolean updateUser(User user);
}
