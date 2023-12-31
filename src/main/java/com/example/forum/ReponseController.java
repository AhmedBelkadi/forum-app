package com.example.forum;

import com.example.forum.dao.ReponseDao;
import com.example.forum.daoImpl.ReponseDaoImpl;
import com.example.forum.models.Question;
import com.example.forum.models.Reponse;




import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


import java.util.List;

public class ReponseController {

    @FXML
    private VBox reponsesContainer;
    @FXML
    private Label labelQuestion;
    private Question qst ;

    public void initData(Question question) {
        labelQuestion.setText("Responses for Question: " + question.getQuestion());
        this.qst = question;
        loadReponses();
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







}
