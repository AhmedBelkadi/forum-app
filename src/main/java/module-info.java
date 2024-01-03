module com.example.forum {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.forum to javafx.fxml;
    exports com.example.forum;

    exports com.example.forum.models;
    exports com.example.forum.dao;
    exports com.example.forum.daoImpl;
    exports com.example.forum.controllers;
    opens com.example.forum.controllers to javafx.fxml;
}