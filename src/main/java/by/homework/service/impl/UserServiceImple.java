package by.homework.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import by.homework.entity.User;
import by.homework.exception.ConnectionException;
import by.homework.exception.ServiceException;
import by.homework.exception.UserNotFoundException;
import by.homework.repository.UserRepository;
import by.homework.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImple implements UserService {

    private final UserRepository userRepository;

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void insertUser(User user) {
        try {
            userRepository.save(user);
        } catch (ConnectionException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<User> selectAllUsers() {

        return userRepository.findAll();

    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not"));

    }

    @Override
    public void updateUser(User user) {
        userRepository.findById(user.getId()).orElseThrow(() -> new UserNotFoundException("User not"));
        userRepository.save(user);

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
