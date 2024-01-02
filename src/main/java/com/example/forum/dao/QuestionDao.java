package com.example.forum.dao;

import com.example.forum.models.Question;

import java.util.List;

public interface QuestionDao {

    List<Question> getAllQuestions();
//    Question getQuestionById(int questionId);
    List<Question> getQuestionsByUserId(int userId);
    void addQuestion(Question question);
    void deleteQst(int questionId);
    void updateQst(Question question);

}
