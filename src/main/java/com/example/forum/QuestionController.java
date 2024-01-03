package com.example.forum;

import com.example.forum.dao.QuestionDao;
import com.example.forum.daoImpl.QuestionDaoImpl;
import com.example.forum.models.Question;
import com.example.forum.models.User;
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

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class QuestionController implements Initializable {

    @FXML
    private HBox navbarHBox;
    @FXML
    private Button logoutButton;
    @FXML
    private Text mesQuestionsButton;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private VBox qqq;
    private void updateNavbarButtonsVisibility() {
        User currentUser = UserSession.getCurrentUser();

        if (currentUser != null) {
            // User is logged in, show the "Logout" and "Mes Questions" buttons
            logoutButton.setVisible(true);
            mesQuestionsButton.setVisible(true);
//             qqq.setVisible(true);

            // Hide the "Login" and "Register" buttons
            loginButton.setVisible(false);
            registerButton.setVisible(false);
        } else {
            // User is not logged in, hide the "Logout" and "Mes Questions" buttons
            logoutButton.setVisible(false);
            mesQuestionsButton.setVisible(false);
//            qqq.setVisible(false);

            // Show the "Login" and "Register" buttons
            loginButton.setVisible(true);
            registerButton.setVisible(true);
        }
    }

    @FXML
    private VBox questionsContainer;
    private Timeline timeline;
    private QuestionDao questionDao = new QuestionDaoImpl();
    private HBox createQuestionCard(Question question) {
        HBox card = new HBox();
        card.setStyle("-fx-background-color: white; -fx-spacing: 100; -fx-padding: 35; -fx-border-radius: 10; -fx-border-color: blue;");

        VBox contentBox = new VBox();
        contentBox.getChildren().addAll(
                createLabel("Question: " + question.getQuestion(), "16px", "bold"),
                createLabel("Date de création: " + question.getDate(), "12px", "#6c757d"),
                createLabel("Créateur: " + question.getUser().getNom() + " (" + question.getUser().getEmail() + ")", "12px", "#6c757d"),
                createButton("Les reponses", event -> showReponsesScene(question))
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
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("reponse.fxml"));
            Scene scene = new Scene(loader.load(), 1100, 800);
            ReponseController responsesController = loader.getController();
            responsesController.initData(question);
            Stage stage = (Stage) questionsContainer.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void showLoginScene( ) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
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
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("register.fxml"));
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
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("mes-questions.fxml"));
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

    @FXML
    private void logout() {
        // Clear the user session
        UserSession.clearSession();
        updateNavbarButtonsVisibility();


    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load questions from the database
        // Load questions from the database initially
        loadQuestions();
        updateNavbarButtonsVisibility();

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
    @FXML
    private Button addQstBtn;
    @FXML
    private TextField qstInput;
    @FXML
    private Label b;
    @FXML
    private void addQuestion() {
        User currentUser = UserSession.getCurrentUser();
        if (currentUser == null){
            b.setText("you should login to add question");return;
        }
        String questionText = qstInput.getText();
            if (!questionText.isEmpty()) {
                Question newQuestion = new Question();
                newQuestion.setQuestion(questionText);
                newQuestion.setUser(currentUser);
                questionDao.addQuestion(newQuestion);
                loadQuestions();
                qstInput.clear();
            }
//            else {
//                // Handle the case where the question text is empty
//                System.out.println("Please enter a question.");
//            }

    }



}
