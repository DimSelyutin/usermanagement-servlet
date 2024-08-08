package by.homework.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.homework.config.ConnectionConfig;
import by.homework.entity.User;
import by.homework.exception.ConnectionException;
import by.homework.repository.UserRepository;

public class UserRepositoryImpl implements UserRepository {
    private static UserRepository instance = null;

    private UserRepositoryImpl() {

    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepositoryImpl();
        }
        return instance;
    }

    Connection connection = null;

    private static final String INSERT_USERS_SQL = "INSERT INTO users (name, email) VALUES (?, ?);";
    private static final String SELECT_USER_BY_ID = "SELECT id, name, email FROM users WHERE id = ?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String DELETE_USER_SQL = "DELETE FROM users WHERE id = ?;";
    private static final String UPDATE_USER_SQL = "UPDATE users SET name = ?, email= ? WHERE id = ?;";

    protected Connection getConnection() {

        connection = ConnectionConfig.getConnection();

        return connection;
    }

    // Create or insert user
    public void createUser(User user) {
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setString(1, user.getFirstname());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new ConnectionException(e.getLocalizedMessage());
        }
    }

    public User findUserById(Long id) {
        User user = null;
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String firstname = rs.getString("firstname");
                String email = rs.getString("email");
                user = new User(id, firstname, email, 0);
            }
        } catch (SQLException e) {
            throw new ConnectionException(e.getLocalizedMessage());
        }
        return user;
    }

    // Select all users
    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Long id = rs.getLong("id");
                String name = rs.getString("firstname");
                String email = rs.getString("email");
                Integer age = rs.getInt("age");
                users.add(new User(id, name, email, age));
            }
        } catch (SQLException e) {
            throw new ConnectionException(e.getLocalizedMessage());
        }
        return users;
    }

    // Delete user by id
    public boolean deleteUser(Long id) {
        boolean rowDeleted;
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_USER_SQL)) {
            statement.setLong(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            rowDeleted = false;
        }
        return rowDeleted;
    }

    // Update user
    public boolean updateUser(User user) {
        boolean rowUpdated;
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_USER_SQL)) {
            statement.setString(1, user.getFirstname());
            statement.setString(2, user.getEmail());
            statement.setLong(3, user.getId());
            rowUpdated = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            rowUpdated = false;
        }
        return rowUpdated;
    }

}
