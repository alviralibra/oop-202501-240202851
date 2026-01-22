package com.upb.agripos;

import com.upb.agripos.controller.LoginController;
import com.upb.agripos.controller.PosController;
import com.upb.agripos.model.User;
import com.upb.agripos.view.LoginView;
import com.upb.agripos.view.PosView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main Application Class untuk Agri-POS
 * Entry point untuk JavaFX application
 * Menampilkan identitas: Hello World, I am [Nama]-[NIM]
 */
public class AppJavaFX extends Application {
    private LoginController loginController;
    private PosController posController;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Print identitas
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║  AGRI-POS - Point of Sale System       ║");
        System.out.println("║  Version 1.0                           ║");
        System.out.println("║  Hello World, I am Haida-240202862     ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        this.primaryStage = primaryStage;

        // Initialize controllers
        this.loginController = new LoginController();
        this.posController = new PosController();

        // Create login view
        LoginView loginView = new LoginView(loginController, this::showMainApplication);

        // Set up primary stage
        primaryStage.setTitle("AGRI-POS - Login");
        primaryStage.setScene(loginView.getScene());
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(e -> {
            System.out.println("✓ Application closed");
            System.exit(0);
        });

        primaryStage.show();
    }

    private void showMainApplication() {
        try {
            User currentUser = loginController.getCurrentUser();
            PosView posView = new PosView(
                posController,
                currentUser.getId(),
                currentUser.getRole()
            );

            primaryStage.setTitle("AGRI-POS - " + currentUser.getRole());
            primaryStage.setScene(posView.getScene());
            primaryStage.setResizable(true);
            
            System.out.println("✓ Main application loaded for user: " + currentUser.getUsername());
        } catch (Exception e) {
            System.err.println("✗ Error loading main application: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void stop() throws Exception {
        System.out.println("✓ Application stopping");
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
