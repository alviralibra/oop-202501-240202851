package com.upb.agripos;

import com.upb.agripos.view.PosView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppJavaFX extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Membuat objek tampilan utama
            PosView posView = new PosView();
            
            // Menggunakan posView langsung sebagai root (karena PosView biasanya extends VBox/BorderPane)
            Scene scene = new Scene(posView, 800, 600);
            
            primaryStage.setTitle("Agri-POS System");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}