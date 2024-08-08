package by.homework.service.impl;

import java.util.List;

import by.homework.entity.User;
import by.homework.exception.ConnectionException;
import by.homework.repository.UserRepository;
import by.homework.repository.impl.UserRepositoryImpl;
import by.homework.service.UserService;

public class UserServiceImple implements UserService {
    private static UserService instance = null;

    private UserServiceImple() {

    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserServiceImple();
        }
        return instance;
    }

    UserRepository userRepository = UserRepositoryImpl.getInstance();

    @Override
    public boolean deleteUser(Long id) {
        return userRepository.deleteUser(id);
    }

    @Override
    public void insertUser(User user) {
        try {
            userRepository.createUser(user);
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

}
