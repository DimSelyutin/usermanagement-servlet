package by.homework.repository.impl;

import by.homework.config.ConnectionConfig;
import by.homework.entity.Role;
import by.homework.entity.User;
import by.homework.exception.ConnectionException;
import by.homework.exception.DaoException;
import by.homework.repository.RoleRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleDaoImpl implements RoleRepository {
    private static final String INSERT_ROLES_SQL = "INSERT INTO roles (name, description) VALUES (?, ?);";
    private static final String SELECT_ROLE_BY_ID = "SELECT * FROM roles WHERE id = ?";
    private static final String SELECT_ALL_ROLES = "SELECT * FROM roles";
    private static final String DELETE_ROLE_SQL = "DELETE FROM roles WHERE id = ?;";
    private static final String UPDATE_ROLE_SQL = "UPDATE roles SET name = ?, description = ? WHERE id = ?";
    private static RoleDaoImpl instance;

    private RoleDaoImpl() {
    }

    public static RoleDaoImpl getInstance() {
        if (instance == null) {
            instance = new RoleDaoImpl();
        }
        return instance;
    }

    Connection connection = null;

    protected Connection getConnection() {

        connection = ConnectionConfig.getConnection();

        return connection;
    }

    @Override
    public void insertRole(Role role) throws DaoException {
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(INSERT_ROLES_SQL,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, role.getName());
            statement.setString(2, role.getDescription());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    role.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error occurred while inserting role");
        }
    }

    @Override
    public Role getRoleById(int id) throws DaoException {
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_ROLE_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Role(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("description"));
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error occurred while getting role by ID");
        }
    }

    @Override
    public void deleteRole(int id) throws DaoException {
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_ROLE_SQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error occurred while deleting role");
        }
    }

    public List<Role> findAllRoles() {
        List<Role> roles = new ArrayList<>();
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ROLES)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String description = rs.getString("description");

                roles.add(new Role(id, name, description));
            }
        } catch (SQLException e) {
            throw new ConnectionException(e.getLocalizedMessage());
        }
        return roles;
    }

    @Override
    public void updateRole(Role role) throws DaoException {
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_ROLE_SQL)) {
            statement.setString(1, role.getName());
            statement.setString(2, role.getDescription());
            statement.setInt(3, role.getId());
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                throw new DaoException("Role with id " + role.getId() + " not found.");
            }
        } catch (SQLException e) {
            throw new DaoException("Error occurred while updating role");
        }
    }

}
