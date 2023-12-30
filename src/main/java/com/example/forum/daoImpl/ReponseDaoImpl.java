package com.example.forum.daoImpl;

import com.example.forum.dao.ReponseDao;
import com.example.forum.models.Reponse;

import java.sql.*;
import java.util.*;

public class ReponseDaoImpl implements ReponseDao {

    private Connection connection;

    public ReponseDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Reponse> getReponsesByQuestionId(int questionId) {
        List<Reponse> reponses = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Reponses WHERE question_id = ?")) {
            preparedStatement.setInt(1, questionId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Reponse reponse = new Reponse();
//                    reponse.setId(resultSet.getInt("id"));
                    reponse.setUserId(resultSet.getInt("user_id"));
                    reponse.setQuestionId(resultSet.getInt("question_id"));
                    reponse.setResponse(resultSet.getString("response"));
//                    reponse.setDate(resultSet.getDate("date"));
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
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Reponses (user_id, question_id, response, date) VALUES (?, ?, ?, CURRENT_TIMESTAMP)")) {
            preparedStatement.setInt(1, reponse.getUserId());
            preparedStatement.setInt(2, reponse.getQuestionId());
            preparedStatement.setString(3, reponse.getResponse());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
