package com.example.forum;

import com.example.forum.dao.UserDao;
import com.example.forum.daoImpl.UserDaoImpl;
import com.example.forum.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserController implements Initializable {

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


    private UserDao userDao = new UserDaoImpl();

    @FXML
    private AnchorPane hh;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField nomFieldr;

    @FXML
    private TextField emailFieldr;

    @FXML
    private PasswordField passwordFieldr;

    @FXML
    private Label errorLabelNom;

    @FXML
    private Label hhh;

    @FXML
    private Label errorLabelEmail;

    @FXML
    private Label errorLabelPassword;
    @FXML
    private Label errorLabelEmailLogin;

    @FXML
    private Label errorLabelPasswordLogin;

    @FXML
    private void login() {
        // Reset error messages and borders
        errorLabelEmailLogin.setText("");
        errorLabelEmailLogin.setStyle("");
        errorLabelPasswordLogin.setText("");
        errorLabelPasswordLogin.setStyle("");

        // Get input values
        String email = emailField.getText();
        String password = passwordField.getText();

        // Validate fields
        boolean isValid = true;

        if (email.isEmpty()) {
            errorLabelEmailLogin.setText("Email is required");
            errorLabelEmailLogin.setStyle("-fx-text-fill: red;");
            isValid = false;
        } else if (!isValidEmail(email)) {
            errorLabelEmailLogin.setText("Invalid email format");
            errorLabelEmailLogin.setStyle("-fx-text-fill: red;");
            isValid = false;
        }

        if (password.isEmpty()) {
            errorLabelPasswordLogin.setText("Password is required");
            errorLabelPasswordLogin.setStyle("-fx-text-fill: red;");
            isValid = false;
        }

        if (!isValid) {
            // Validation failed, stop login
            return;
        }

        // Check if the user exists
        Optional<User> userOptional = userDao.getUserByEmail(email);

        if (userOptional.isPresent() && userDao.checkPassword(userOptional.get(), password)) {
            UserSession.setCurrentUser(userOptional.get());
            System.out.println("Login successful");
            updateNavbarButtonsVisibility();

            // Redirect to the home or user's questions scene

            try {
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
                Scene scene = new Scene(loader.load(), 1100, 800);
                Stage stage = (Stage) emailField.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            errorLabelEmailLogin.setText("Invalid credentials");
            errorLabelEmailLogin.setStyle("-fx-text-fill: red;");
            System.out.println("Invalid credentials");
            // Handle invalid credentials
        }
    }
    @FXML
    private void DoRegister() {
        // Reset error messages and borders
        errorLabelNom.setText("");
        errorLabelNom.setStyle("");
        errorLabelEmail.setText("");
        errorLabelEmail.setStyle("");
        errorLabelPassword.setText("");
        errorLabelPassword.setStyle("");

        // Get input values
        String nom = nomFieldr.getText();
        String email = emailFieldr.getText();
        String password = passwordFieldr.getText();

        // Validate fields
        boolean isValid = true;

        if (nom.isEmpty()) {
            errorLabelNom.setText("Nom is required");
            errorLabelNom.setStyle("-fx-text-fill: red;");
            isValid = false;
        }

        if (email.isEmpty()) {
            errorLabelEmail.setText("Email is required");
            errorLabelEmail.setStyle("-fx-text-fill: red;");
            isValid = false;
        } else if (!isValidEmail(email)) {
            errorLabelEmail.setText("Invalid email format");
            errorLabelEmail.setStyle("-fx-text-fill: red;");
            isValid = false;
        }

        if (password.isEmpty()) {
            errorLabelPassword.setText("Password is required");
            errorLabelPassword.setStyle("-fx-text-fill: red;");
            isValid = false;
        }

        if (!isValid) {
            // Validation failed, stop registration
            return;
        }

        // Check if the user already exists
        Optional<User> existingUserOptional = userDao.getUserByEmail(email);

        if (existingUserOptional.isPresent()) {
            // User with the same email already exists
            errorLabelEmail.setText("User with the same email already exists");
            errorLabelEmail.setStyle("-fx-text-fill: red;");
            System.out.println("User with the same email already exists");
            // Handle the case where registration fails
        } else {
            // Create a new user and save to the database
            User newUser = new User();
            newUser.setNom(nom);
            newUser.setEmail(email);
            newUser.setPassword(password);
            userDao.saveUser(newUser);
            System.out.println("Registration successful");


            // Redirect to the login page or perform other actions after successful registration
            try {
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
                Scene scene = new Scene(loader.load(), 1100, 800);
                Stage stage = (Stage) nomFieldr.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private boolean isValidEmail(String email) {
        // You can implement a more sophisticated email validation if needed
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }

    @FXML
    private void showHomeScene( ) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(loader.load(), 1100, 800);

            Stage stage = (Stage) hhh.getScene().getWindow();
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

            Stage stage = (Stage) hhh.getScene().getWindow();
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
            Stage stage = (Stage) hh.getScene().getWindow();
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
            Stage stage = (Stage) hhh.getScene().getWindow();

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
        updateNavbarButtonsVisibility();

    }
}
