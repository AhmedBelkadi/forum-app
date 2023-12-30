package com.example.forum;

import com.example.forum.dao.QuestionDao;
import com.example.forum.daoImpl.QuestionDaoImpl;
import com.example.forum.models.Question;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class QuestionController implements Initializable {

    @FXML
    private VBox questionsContainer;

    private Timeline timeline;

    // Assume you have a QuestionDao instance
    private QuestionDao questionDao = new QuestionDaoImpl();

//    @FXML
//    public void initialize() {
//
//    }

    private HBox createQuestionCard(Question question) {
        HBox card = new HBox();
        card.setStyle("-fx-background-color: #f8d7da; -fx-padding: 10; -fx-spacing: 10; -fx-border-radius: 5; -fx-border-color: #dc3545;");

        VBox contentBox = new VBox();
        contentBox.getChildren().addAll(
                createLabel("Question: " + question.getQuestion(), "16px", "bold"),
                createLabel("Date de création: " + question.getDate(), "12px", "#6c757d"),
                createLabel("Créateur: " + question.getNomUser() + " (" + question.getEmailUser() + ")", "12px", "#6c757d"),
                createTextArea(question.getQuestion()),
                createButton("Répondre", event -> showReponsesScene(question))
        );
//                createButton("Répondre", event -> showReponsesScene(question))

        card.getChildren().add(contentBox);
        return card;
    }

    private Label createLabel(String text, String fontSize, String style) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: " + fontSize + "; -fx-font-weight: " + style + ";");
        return label;
    }

    private TextArea createTextArea(String text) {
        TextArea textArea = new TextArea(text);
        textArea.setStyle("-fx-font-size: 14px;");
        textArea.setEditable(false);
        textArea.setWrapText(true);
        return textArea;
    }

    private Button createButton(String text, EventHandler<ActionEvent> eventHandler) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 5 10;");
        button.setOnAction(eventHandler);
        return button;
    }

    private void showReponsesScene(Question question) {
        // Implement logic to show responses scene for the selected question
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
        List<Question> questions = questionDao.getAllQuestions();

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
