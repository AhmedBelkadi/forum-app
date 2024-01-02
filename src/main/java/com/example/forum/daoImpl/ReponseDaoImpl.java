package com.example.forum.daoImpl;

import com.example.forum.dao.ReponseDao;
import com.example.forum.models.Question;
import com.example.forum.models.Reponse;
import com.example.forum.models.User;

import java.sql.*;
import java.util.*;

public class ReponseDaoImpl implements ReponseDao {

    private String jdbcURL = "jdbc:mysql://localhost:3306/forum";
    private String jdbcUsername = "root";
    private String jdbcPassword = "";
    private Connection connection;
    public ReponseDaoImpl() {
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
    public List<Reponse> getReponsesByQuestionId(int questionId) {
        List<Reponse> reponses = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT r.* , u.nom , u.email  FROM reponses as r  , users as u WHERE question_id = ? AND r.user_id = u.id ORDER BY r.id desc ")) {
            preparedStatement.setInt(1, questionId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Reponse reponse = new Reponse();
                    User user = new User();
                    Question question = new Question();
                    reponse.setId(resultSet.getInt("id"));
                    reponse.setResponse(resultSet.getString("reponse"));
                    reponse.setDate(resultSet.getString("date"));
                    user.setId(resultSet.getInt("user_id"));
                    user.setNom(resultSet.getString("nom"));
                    user.setEmail(resultSet.getString("email"));
                    question.setId(resultSet.getInt("question_id"));
                    reponse.setUser(user);
                    reponse.setQuestion(question);

                    reponses.add(reponse);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reponses;
    }
    @Override
    public void addReponse(Reponse reponse) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO reponses (user_id, question_id, reponse, date) VALUES (?, ?, ?, CURRENT_TIMESTAMP)")) {
            preparedStatement.setInt(1, reponse.getUser().getId());
            preparedStatement.setInt(2, reponse.getQuestion().getId());
            preparedStatement.setString(3, reponse.getResponse());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
