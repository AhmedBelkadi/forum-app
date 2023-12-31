package com.example.forum.models;

public class Reponse {
    private int id;
    private String response;
    private String date;
    private User user;
    private Question question;

    public Reponse(String response, String date, User user, Question question) {
        this.response = response;
        this.date = date;
        this.user = user;
        this.question = question;
    }

    public Reponse() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
