package com.example.forum.daoImpl;

import com.example.forum.dao.UserDao;
import com.example.forum.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private String jdbcURL = "jdbc:mysql://localhost:3306/forum";
    private String jdbcUsername = "root";
    private String jdbcPassword = "";
    private Connection connection;
    public UserDaoImpl() {
        this.connection = getConnection();
    }
    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToUser(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
    @Override
    public boolean checkPassword(User user, String password) {
        // Implement logic to check if the provided password matches the stored password
        return user != null && user.getPassword().equals(password);
    }
    @Override
    public void saveUser(User user) {
        String sql = "INSERT INTO Users (nom, email, password) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, user.getNom());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }
    private User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        // Map the ResultSet to a User object
        int id = resultSet.getInt("id");
        String name = resultSet.getString("nom");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");

        return new User(id, name, email, password);
    }

}

