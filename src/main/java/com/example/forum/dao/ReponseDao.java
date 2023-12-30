package com.example.forum.dao;

import com.example.forum.models.Reponse;

import java.util.List;

public interface ReponseDao {
    List<Reponse> getReponsesByQuestionId(int questionId);
    void addReponse(Reponse reponse);
}
