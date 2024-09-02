package by.homework.service;

import java.util.List;

import by.homework.entity.User;

public interface UserService {


    // Create or insert user
    public void insertUser(User user);

    // Select user by id
    public User getUserById(Long id);

    // Select all users
    public List<User> selectAllUsers();

    // Delete user by id
    public boolean deleteUser(Long id);

    // Update user
    public boolean updateUser(User user);
    void assignRoleToUser(Long userId, Long roleId);

}