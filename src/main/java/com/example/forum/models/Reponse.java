package com.example.forum.models;

public class Reponse {
    private int id;
    private int userId;
    private int questionId;
    private String response;
    private String date;

    public Reponse(int userId, int questionId, String response, String date) {
        this.userId = userId;
        this.questionId = questionId;
        this.response = response;
        this.date = date;
    }
    public Reponse() {
    }

    public int getId() {
        return id;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
