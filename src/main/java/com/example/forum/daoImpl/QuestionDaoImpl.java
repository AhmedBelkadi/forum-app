package com.example.forum.daoImpl;

import com.example.forum.dao.QuestionDao;
import com.example.forum.models.Question;
import com.example.forum.models.User;

import java.sql.*;
import java.sql.ResultSet;
import java.util.*;

public class QuestionDaoImpl implements QuestionDao {

    private String jdbcURL = "jdbc:mysql://localhost:3306/forumee";
    private String jdbcUsername = "root";
    private String jdbcPassword = "";
    private Connection connection;
    public QuestionDaoImpl() {
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
    public List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT q.* , u.nom , u.email FROM questions as q , users as u WHERE " +
                                                                                    "q.user_id = u.id ORDER BY q.id desc ")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Question question = new Question();
                    User user = new User();
                    question.setId(resultSet.getInt("id"));
                    question.setQuestion(resultSet.getString("question"));
                    question.setDate(resultSet.getString("date"));
                    user.setNom(resultSet.getString("nom"));
                    user.setEmail(resultSet.getString("email"));
                    question.setUser(user);

                    questions.add(question);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }
    @Override
    public List<Question> getQuestionsByUserId(int userId) {



        List<Question> questions = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT q.* , u.nom , u.email FROM questions as q , users as u WHERE " +
                "q.user_id = u.id AND q.user_id = ? ORDER BY q.id desc ")) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Question question = new Question();
                    User user = new User();
                    question.setId(resultSet.getInt("id"));
                    question.setQuestion(resultSet.getString("question"));
                    question.setDate(resultSet.getString("date"));
                    user.setNom(resultSet.getString("nom"));
                    user.setEmail(resultSet.getString("email"));
                    question.setUser(user);

                    questions.add(question);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;

    }
    @Override
    public void addQuestion(Question question) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Questions (user_id, question, date) VALUES (?, ?, CURRENT_TIMESTAMP)")) {
            preparedStatement.setInt(1, question.getUser().getId());
            preparedStatement.setString(2, question.getQuestion());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteQst(int questionId) {
        String sql = "DELETE FROM questions WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, questionId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void updateQst(Question question) {
        String sql = "UPDATE Questions SET question = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, question.getQuestion());
            preparedStatement.setInt(3, question.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
