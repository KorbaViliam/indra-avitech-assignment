package com.vkorba.persistance;


import com.vkorba.dtos.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.vkorba.persistance.SqlUtils.*;

public class UserRepositoryImpl implements UserRepository {
    private Connection connection;
    private static UserRepositoryImpl instance;

    private UserRepositoryImpl() {
        try {
            connection = DriverManager.getConnection("jdbc:h2:mem:demo;DB_CLOSE_DELAY=-1");
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static UserRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new UserRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void save(String userName) {
        try (PreparedStatement statement = connection.prepareStatement(getInsertUserScript())) {
            statement.setString(1, userName);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAll() {
        var users = new ArrayList<User>();
        try (Statement statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(getSelectAllUsersScript());
            while (resultSet.next()) {
                users.add(mapToUser(resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void deleteAll() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(getDeleteAllUsersScript());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(getCreateTableUsersScript());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private User mapToUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getString("ID"),
                resultSet.getString("GUID"),
                resultSet.getString("NAME")
        );
    }
}
