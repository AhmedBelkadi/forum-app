package com.example.forum.daoImpl;

import com.example.forum.dao.QuestionDao;
import com.example.forum.models.Question;

import java.sql.*;
import java.sql.ResultSet;
import java.util.*;

public class QuestionDaoImpl implements QuestionDao {

    private String jdbcURL = "jdbc:mysql://localhost:3306/forum";
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
                    question.setId(resultSet.getInt("id"));
                    question.setUserId(resultSet.getInt("user_id"));
                    question.setQuestion(resultSet.getString("question"));
                    question.setDate(resultSet.getString("date"));
                    question.setNomUser(resultSet.getString("nom"));
                    question.setEmailUser(resultSet.getString("email"));
                    questions.add(question);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    @Override
    public Question getQuestionById(int questionId) {
        Question question = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Questions WHERE id = ?")) {
            preparedStatement.setInt(1, questionId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    question = new Question();
//                    question.setId(resultSet.getInt("id"));
                    question.setUserId(resultSet.getInt("user_id"));
                    question.setQuestion(resultSet.getString("question"));
//                    question.setDate(resultSet.getDate("date"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return question;
    }

    @Override
    public List<Question> getQuestionsByUserId(int userId) {
        List<Question> questions = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Questions WHERE user_id = ?")) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Question question = new Question();
//                    question.setId(resultSet.getInt("id"));
                    question.setUserId(resultSet.getInt("user_id"));
                    question.setQuestion(resultSet.getString("question"));
//                    question.setDate(resultSet.getDate("date"));
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
            preparedStatement.setInt(1, question.getUserId());
            preparedStatement.setString(2, question.getQuestion());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
