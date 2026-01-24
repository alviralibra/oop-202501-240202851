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
import javafx.scene.shape.Line;
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
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #16a085, #1abc9c);");

        // Center - Login Form
        VBox centerBox = createLoginForm();
        centerBox.setAlignment(Pos.CENTER);
        root.setCenter(centerBox);

        scene = new Scene(root, 500, 650);
    }

    private VBox createLoginForm() {
        VBox mainVbox = new VBox(20);
        mainVbox.setAlignment(Pos.CENTER);
        mainVbox.setPadding(new Insets(30));

        // White card container
        VBox card = new VBox(20);
        card.setAlignment(Pos.TOP_CENTER);
        card.setPadding(new Insets(40));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10; " +
                      "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 5);");

        // Header section
        VBox headerBox = new VBox(10);
        headerBox.setAlignment(Pos.CENTER);

        // Logo
        Label logoLabel = new Label("🌾");
        logoLabel.setFont(Font.font("Arial", 48));

        Label titleLabel = new Label("AGRI-POS");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        titleLabel.setStyle("-fx-text-fill: #1abc9c;");

        Label subtitleLabel = new Label("Point of Sale System");
        subtitleLabel.setFont(Font.font("Arial", 13));
        subtitleLabel.setStyle("-fx-text-fill: #95a5a6;");

        headerBox.getChildren().addAll(logoLabel, titleLabel, subtitleLabel);
        card.getChildren().add(headerBox);

        // Divider
        javafx.scene.shape.Line divider = new javafx.scene.shape.Line(0, 0, 250, 0);
        divider.setStyle("-fx-stroke: #ecf0f1; -fx-stroke-width: 1;");
        card.getChildren().add(divider);

        // Username section
        Label usernameLabel = new Label("👤 Username");
        usernameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        usernameLabel.setStyle("-fx-text-fill: #2c3e50;");

        usernameField = new TextField();
        usernameField.setPromptText("Masukkan username Anda");
        usernameField.setPrefHeight(40);
        usernameField.setPrefWidth(300);
        usernameField.setStyle("-fx-padding: 10; -fx-font-size: 12; -fx-border-color: #ecf0f1; " +
                               "-fx-border-radius: 5; -fx-background-radius: 5; -fx-border-width: 1;");
        usernameField.setOnMouseEntered(e -> usernameField.setStyle(
                "-fx-padding: 10; -fx-font-size: 12; -fx-border-color: #1abc9c; " +
                "-fx-border-radius: 5; -fx-background-radius: 5; -fx-border-width: 2;"));
        usernameField.setOnMouseExited(e -> usernameField.setStyle(
                "-fx-padding: 10; -fx-font-size: 12; -fx-border-color: #ecf0f1; " +
                "-fx-border-radius: 5; -fx-background-radius: 5; -fx-border-width: 1;"));

        VBox usernameBox = new VBox(8);
        usernameBox.getChildren().addAll(usernameLabel, usernameField);

        // Password section
        Label passwordLabel = new Label("🔐 Password");
        passwordLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        passwordLabel.setStyle("-fx-text-fill: #2c3e50;");

        passwordField = new PasswordField();
        passwordField.setPromptText("Masukkan password Anda");
        passwordField.setPrefHeight(40);
        passwordField.setPrefWidth(300);
        passwordField.setStyle("-fx-padding: 10; -fx-font-size: 12; -fx-border-color: #ecf0f1; " +
                               "-fx-border-radius: 5; -fx-background-radius: 5; -fx-border-width: 1;");
        passwordField.setOnMouseEntered(e -> passwordField.setStyle(
                "-fx-padding: 10; -fx-font-size: 12; -fx-border-color: #1abc9c; " +
                "-fx-border-radius: 5; -fx-background-radius: 5; -fx-border-width: 2;"));
        passwordField.setOnMouseExited(e -> passwordField.setStyle(
                "-fx-padding: 10; -fx-font-size: 12; -fx-border-color: #ecf0f1; " +
                "-fx-border-radius: 5; -fx-background-radius: 5; -fx-border-width: 1;"));

        VBox passwordBox = new VBox(8);
        passwordBox.getChildren().addAll(passwordLabel, passwordField);

        card.getChildren().addAll(usernameBox, passwordBox);

        // Message Label
        messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 11;");
        messageLabel.setWrapText(true);
        card.getChildren().add(messageLabel);

        // Login Button
        loginButton = new Button("🚀 LOGIN");
        loginButton.setPrefWidth(300);
        loginButton.setPrefHeight(45);
        loginButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        loginButton.setStyle("-fx-background-color: linear-gradient(to right, #16a085, #1abc9c); " +
                             "-fx-text-fill: white; -fx-border-radius: 5; -fx-background-radius: 5; " +
                             "-fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);");
        loginButton.setOnMouseEntered(e -> loginButton.setStyle(
                "-fx-background-color: linear-gradient(to right, #139d7d, #15a896); " +
                "-fx-text-fill: white; -fx-border-radius: 5; -fx-background-radius: 5; " +
                "-fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0, 0, 3);"));
        loginButton.setOnMouseExited(e -> loginButton.setStyle(
                "-fx-background-color: linear-gradient(to right, #16a085, #1abc9c); " +
                "-fx-text-fill: white; -fx-border-radius: 5; -fx-background-radius: 5; " +
                "-fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);"));
        loginButton.setOnAction(e -> handleLogin());

        card.getChildren().add(loginButton);

        // Footer - Demo credentials
        VBox footerBox = new VBox(5);
        footerBox.setAlignment(Pos.CENTER);
        
        Label infoTitle = new Label("📋 Demo Credentials");
        infoTitle.setFont(Font.font("Arial", FontWeight.BOLD, 11));
        infoTitle.setStyle("-fx-text-fill: #7f8c8d;");

        Label kasirInfo = new Label("KASIR: kasir001 / pass123");
        kasirInfo.setStyle("-fx-font-size: 10; -fx-text-fill: #95a5a6;");

        Label adminInfo = new Label("ADMIN: admin001 / admin123");
        adminInfo.setStyle("-fx-font-size: 10; -fx-text-fill: #95a5a6;");

        footerBox.getChildren().addAll(infoTitle, kasirInfo, adminInfo);
        card.getChildren().add(footerBox);

        mainVbox.getChildren().add(card);
        return mainVbox;
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
