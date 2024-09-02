package by.homework.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.homework.entity.User;
import by.homework.exception.ConnectionException;
import by.homework.exception.ServiceException;
import by.homework.repository.RoleRepository;
import by.homework.repository.UserRepository;
import by.homework.repository.impl.RoleRepositoryImpl;
import by.homework.repository.impl.UserRepositoryImpl;
import by.homework.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class UserServiceImple implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public boolean deleteUser(Long id) {
        return userRepository.deleteUser(id);
    }

    @Override
    public void insertUser(User user) {
        try {
            userRepository.saveUser(user);
        } catch (ConnectionException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<User> selectAllUsers() {

        return userRepository.findAllUsers();

    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findUserById(id);

    }

    @Override
    public boolean updateUser(User user) {
        return userRepository.updateUser(user);

    }

    @Override
    public void assignRoleToUser(Long userId, Long roleId) {
        try {

            userRepository.assignRoleToUser(userId, roleId);

        } catch (Exception e) {
            throw new ServiceException("Error occurred while assigning role to user", e);
        }
    }

}
