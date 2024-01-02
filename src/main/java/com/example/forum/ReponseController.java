package com.example.forum;

import com.example.forum.dao.ReponseDao;
import com.example.forum.daoImpl.ReponseDaoImpl;
import com.example.forum.models.Question;
import com.example.forum.models.Reponse;


import com.example.forum.models.User;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.List;

public class ReponseController {

    @FXML
    private HBox navbarHBox;

    @FXML
    private Button logoutButton;

    @FXML
    private Button mesQuestionsButton;
    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;


    @FXML
    private VBox rrr;


    private void updateNavbarButtonsVisibility() {
        User currentUser = UserSession.getCurrentUser();

        if (currentUser != null) {
            // User is logged in, show the "Logout" and "Mes Questions" buttons
            logoutButton.setVisible(true);
            mesQuestionsButton.setVisible(true);
//            rrr.setVisible(true);

            // Hide the "Login" and "Register" buttons
            loginButton.setVisible(false);
            registerButton.setVisible(false);
        } else {
            // User is not logged in, hide the "Logout" and "Mes Questions" buttons
            logoutButton.setVisible(false);
            mesQuestionsButton.setVisible(false);
//            rrr.setVisible(false);

            // Show the "Login" and "Register" buttons
            loginButton.setVisible(true);
            registerButton.setVisible(true);
        }
    }

    @FXML
    private VBox reponsesContainer;
    @FXML
    private Label labelQuestion1;
    private Question qst ;

    public void initData(Question question) {
        labelQuestion1.setText("Responses for Question: " + question.getQuestion());
        this.qst = question;
        loadReponses();
        updateNavbarButtonsVisibility();

    }

    private ReponseDao reponseDao = new ReponseDaoImpl();

    private HBox createReponseCard(Reponse reponse) {
        HBox card = new HBox();
        card.setStyle("-fx-background-color: white; -fx-spacing: 100; -fx-padding: 35; -fx-border-radius: 10; -fx-border-color: blue;");

        VBox contentBox = new VBox();
        contentBox.getChildren().addAll(
                createLabel("Reponse: " + reponse.getResponse(), "16px", "bold"),
                createLabel("Date de création: " + reponse.getDate(), "12px", "#6c757d"),
                createLabel("Créateur: " + reponse.getUser().getNom() + " (" + reponse.getUser().getEmail() + ")", "12px", "#6c757d")
        );
//                createButton("Les reponses", event -> showReponsesScene(question))


        card.getChildren().add(contentBox);
        return card;
    }

    private Label createLabel(String text, String fontSize, String style) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: " + fontSize + "; -fx-font-weight: " + style + ";");
        return label;
    }

    private void loadReponses() {
        // Load questions from the database
        List<Reponse> reponses = reponseDao.getReponsesByQuestionId(qst.getId());

        // Clear existing questions
        reponsesContainer.getChildren().clear();

        // Display questions dynamically
        for (Reponse reponse : reponses) {
            HBox questionCard = createReponseCard(reponse);
            reponsesContainer.getChildren().add(questionCard);
        }
    }

    @FXML
    private void showLoginScene( ) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
            Scene scene = new Scene(loader.load(), 1100, 800);

            Stage stage = (Stage) reponsesContainer.getScene().getWindow();
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
            Stage stage = (Stage) reponsesContainer.getScene().getWindow();
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
            Stage stage = (Stage) reponsesContainer.getScene().getWindow();

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
        UserSession.clearSession();
        updateNavbarButtonsVisibility();
    }

    @FXML
    private void showHomeScene( ) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(loader.load(), 1100, 800);

            Stage stage = (Stage) reponsesContainer.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private TextField rpsInput;
    @FXML
    private Label b;
    @FXML
    private void addReponse(){
        User currentUser = UserSession.getCurrentUser();
        if (currentUser == null){
            b.setText("you should login to add reponse");return;
        }
        String reponseText = rpsInput.getText();
        if (!reponseText.isEmpty()) {
            Reponse newReponse = new Reponse();
            newReponse.setResponse(reponseText);
            newReponse.setUser(currentUser);
            newReponse.setQuestion(qst);
            reponseDao.addReponse(newReponse);
            loadReponses();
            rpsInput.clear();
        }
    }







}
