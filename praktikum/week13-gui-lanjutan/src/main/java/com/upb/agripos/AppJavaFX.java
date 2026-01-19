package com.upb.agripos;

import com.upb.agripos.view.ProductTableView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppJavaFX extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Kita panggil tanpa isi kurung karena kodenya sudah otomatis
        ProductTableView productTableView = new ProductTableView();

        Scene scene = new Scene(productTableView, 800, 600);
        primaryStage.setTitle("Agri-POS Alvira Libra -240202851");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}