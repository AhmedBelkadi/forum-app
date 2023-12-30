package com.example.forum.models;

public class Question {

    private int id;
    private String question;
    private String date;
    private int userId;
    private String nomUser;
    private String emailUser;

    public Question() {
    }

    public Question(String question, String date, int userId, String nomUser, String emailUser) {
        this.question = question;
        this.date = date;
        this.userId = userId;
        this.nomUser = nomUser;
        this.emailUser = emailUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNomUser() {
        return nomUser;
    }

    public void setNomUser(String nomUser) {
        this.nomUser = nomUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }
}
