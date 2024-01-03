package com.example.forum.controllers;

import com.example.forum.Application;
import com.example.forum.dao.QuestionDao;
import com.example.forum.daoImpl.QuestionDaoImpl;
import com.example.forum.models.Question;
import com.example.forum.models.User;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MesQuestionsController implements Initializable {

    @FXML
    private HBox navbarHBox;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Text mesQuestionsButton;

    private void updateNavbarButtonsVisibility() {
        User currentUser = UserSession.getCurrentUser();

        if (currentUser != null) {
            // User is logged in, show the "Logout" and "Mes Questions" buttons
            logoutButton.setVisible(true);
            mesQuestionsButton.setVisible(true);

            // Hide the "Login" and "Register" buttons
            loginButton.setVisible(false);
            registerButton.setVisible(false);
        } else {
            // User is not logged in, hide the "Logout" and "Mes Questions" buttons
            logoutButton.setVisible(false);
            mesQuestionsButton.setVisible(false);

            // Show the "Login" and "Register" buttons
            loginButton.setVisible(true);
            registerButton.setVisible(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadQuestions();
        updateNavbarButtonsVisibility();

    }

    @FXML
    private VBox questionsContainer;
    private Timeline timeline;
    private QuestionDao questionDao = new QuestionDaoImpl();
    private void loadQuestions() {
        // Load questions from the database
        List<Question> questions = questionDao.getQuestionsByUserId(UserSession.getCurrentUser().getId());

        // Clear existing questions
        questionsContainer.getChildren().clear();

        // Display questions dynamically
        for (Question question : questions) {
            HBox questionCard = createQuestionCard(question);
            questionsContainer.getChildren().add(questionCard);
        }
    }
    private void showReponsesScene(Question question) {
        try {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("reponse.fxml"));
            Scene scene = new Scene(loader.load(), 1100, 800);
            ReponseController responsesController = loader.getController();
            responsesController.initData(question);
            Stage stage = (Stage) questionsContainer.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void deleteQst(int id) {
        System.out.println(id);
        questionDao.deleteQst(id);
        loadQuestions();
    }
    private HBox createQuestionCard(Question question) {
        HBox card = new HBox();
        VBox contentBox = new VBox();
        HBox card_btn = new HBox();

        card.setStyle("-fx-background-color: white; -fx-spacing: 100; -fx-padding: 35; -fx-border-radius: 10; -fx-border-color: blue;");
        card_btn.getChildren().addAll( createButton("Les reponses", event -> showReponsesScene(question) , "blue" ) ,
                createButton("delete", event -> deleteQst(question.getId()) , "red" ));
        card_btn.setStyle("-fx-spacing: 10");
//                createButton("update", event -> updateQst(question) , "green" ),
        contentBox.getChildren().addAll(
                createLabel("Question: " + question.getQuestion(), "16px", "bold"),
                createLabel("Date de création: " + question.getDate(), "12px", "#6c757d"),
                createLabel("Créateur: " + question.getUser().getNom() + " (" + question.getUser().getEmail() + ")", "12px", "#6c757d"),
                card_btn

        );


        card.getChildren().add(contentBox);
        return card;
    }
    private Label createLabel(String text, String fontSize, String style) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: " + fontSize + "; -fx-font-weight: " + style + ";");
        return label;
    }
    private Button createButton(String text, EventHandler<ActionEvent> eventHandler , String color ) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: "+color+" blue; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 5 10;-fx-margin:5 0;");
        button.setOnAction(eventHandler);
        return button;
    }


    @FXML
    private void showHomeScene( ) {
        try {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("home.fxml"));
            Scene scene = new Scene(loader.load(), 1100, 800);

            Stage stage = (Stage) questionsContainer.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void showLoginScene( ) {
        try {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("login.fxml"));
            Scene scene = new Scene(loader.load(), 1100, 800);

            Stage stage = (Stage) questionsContainer.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void showRegisterScene( ) {
        try {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("register.fxml"));
            Scene scene = new Scene(loader.load(), 1100, 800);
            Stage stage = (Stage) questionsContainer.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void logout() {
        // Clear the user session
        UserSession.clearSession();
        updateNavbarButtonsVisibility();

        // Redirect to the login page or perform other actions after logout
        try {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("home.fxml"));
            Scene scene = new Scene(loader.load(), 1100, 800);
            Stage stage = (Stage) questionsContainer.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void showMesQstsScene() {
        // Implement logic to show responses scene for the selected question
        try {
            // Load the ResponsesScene.fxml file
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("mes-questions.fxml"));
            Scene scene = new Scene(loader.load(), 1100, 800);


            // Get the current stage
            Stage stage = (Stage) questionsContainer.getScene().getWindow();

            // Update the scene of the current stage
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
//        System.out.println(question.getId());
    }


}
