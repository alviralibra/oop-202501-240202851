package com.upb.agripos.view;

import com.upb.agripos.controller.LoginController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * LoginView - JavaFX GUI untuk Login
 * Tampilan sederhana untuk autentikasi user
 */
public class LoginView {
    private Scene scene;
    private TextField usernameField;
    private PasswordField passwordField;
    private Label messageLabel;
    private Button loginButton;
    private LoginController controller;
    private Runnable onLoginSuccess;

    public LoginView(LoginController controller, Runnable onLoginSuccess) {
        this.controller = controller;
        this.onLoginSuccess = onLoginSuccess;
        initializeUI();
    }

    private void initializeUI() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f5f5;");

        // Center - Login Form
        VBox centerBox = createLoginForm();
        root.setCenter(centerBox);

        scene = new Scene(root, 400, 300);
    }

    private VBox createLoginForm() {
        VBox vbox = new VBox(15);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(40));
        vbox.setStyle("-fx-background-color: white;");

        // Title
        Label titleLabel = new Label("AGRI-POS LOGIN");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        vbox.getChildren().add(titleLabel);

        // Username
        Label usernameLabel = new Label("Username:");
        usernameLabel.setFont(Font.font("Arial", 12));
        usernameField = new TextField();
        usernameField.setPromptText("masukkan username");
        usernameField.setPrefWidth(250);
        VBox usernameBox = new VBox(5);
        usernameBox.getChildren().addAll(usernameLabel, usernameField);
        vbox.getChildren().add(usernameBox);

        // Password
        Label passwordLabel = new Label("Password:");
        passwordLabel.setFont(Font.font("Arial", 12));
        passwordField = new PasswordField();
        passwordField.setPromptText("masukkan password");
        passwordField.setPrefWidth(250);
        VBox passwordBox = new VBox(5);
        passwordBox.getChildren().addAll(passwordLabel, passwordField);
        vbox.getChildren().add(passwordBox);

        // Message Label
        messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: #ff0000;");
        vbox.getChildren().add(messageLabel);

        // Login Button
        loginButton = new Button("LOGIN");
        loginButton.setPrefWidth(250);
        loginButton.setPrefHeight(40);
        loginButton.setStyle("-fx-font-size: 14; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        loginButton.setOnAction(e -> handleLogin());
        vbox.getChildren().add(loginButton);

        // Demo info
        Label demoLabel = new Label("Demo: kasir001/pass123 atau admin001/admin123");
        demoLabel.setStyle("-fx-font-size: 10; -fx-text-fill: #666666;");
        vbox.getChildren().add(demoLabel);

        return vbox;
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Username dan password tidak boleh kosong");
            return;
        }

        try {
            controller.authenticate(username, password);
            messageLabel.setText("");
            if (onLoginSuccess != null) {
                onLoginSuccess.run();
            }
        } catch (Exception e) {
            messageLabel.setText("Login gagal: " + e.getMessage());
        }
    }

    public Scene getScene() {
        return scene;
    }

    public void clearFields() {
        usernameField.clear();
        passwordField.clear();
        messageLabel.setText("");
    }
}
