package com.example.forum;

import com.example.forum.dao.QuestionDao;
import com.example.forum.daoImpl.QuestionDaoImpl;
import com.example.forum.models.Question;
import javafx.animation.KeyFrame;
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
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MesQuestionsController implements Initializable {

    @FXML
    private VBox questionsContainer;

    private Timeline timeline;

    // Assume you have a QuestionDao instance
    private QuestionDao questionDao = new QuestionDaoImpl();



    private HBox createQuestionCard(Question question) {
        HBox card = new HBox();
        card.setStyle("-fx-background-color: white; -fx-spacing: 100; -fx-padding: 35; -fx-border-radius: 10; -fx-border-color: blue;");

        VBox contentBox = new VBox();
        Button buttonU = new Button("update");
        buttonU.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 5 10;-fx-margin:5 0;");
        Button buttonD = new Button("delete");
        buttonD.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 5 10;-fx-margin:5 0;");
        contentBox.getChildren().addAll(
                createLabel("Question: " + question.getQuestion(), "16px", "bold"),
                createLabel("Date de création: " + question.getDate(), "12px", "#6c757d"),
                createLabel("Créateur: " + question.getUser().getNom() + " (" + question.getUser().getEmail() + ")", "12px", "#6c757d"),
                createButton("Les reponses", event -> showReponsesScene(question)),
                buttonD,
                buttonU
        );


        card.getChildren().add(contentBox);
        return card;
    }

    private Label createLabel(String text, String fontSize, String style) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: " + fontSize + "; -fx-font-weight: " + style + ";");
        return label;
    }



    private Button createButton(String text, EventHandler<ActionEvent> eventHandler) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: blue; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 5 10;-fx-margin:5 0;");
        button.setOnAction(eventHandler);
        return button;
    }

    private void showReponsesScene(Question question) {
        // Implement logic to show responses scene for the selected question
        try {
            // Load the ResponsesScene.fxml file
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("reponse.fxml"));
            Scene scene = new Scene(loader.load(), 1100, 800);

            // Get the controller from the loader
            ReponseController responsesController = loader.getController();

            // Pass the selected question to the ResponsesController
            responsesController.initData(question);

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



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load questions from the database
        // Load questions from the database initially
        loadQuestions();

        // Set up a timeline to refresh questions every 30 seconds
        setupTimeline();
    }

    private void loadQuestions() {
        // Load questions from the database
        List<Question> questions = questionDao.getQuestionsByUserId(11);

        // Clear existing questions
        questionsContainer.getChildren().clear();

        // Display questions dynamically
        for (Question question : questions) {
            HBox questionCard = createQuestionCard(question);
            questionsContainer.getChildren().add(questionCard);
        }
    }

    private void setupTimeline() {
        // Create a timeline that triggers every 30 seconds
        timeline = new Timeline(new KeyFrame(Duration.seconds(9), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Load and display questions from the database
                loadQuestions();
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

}
