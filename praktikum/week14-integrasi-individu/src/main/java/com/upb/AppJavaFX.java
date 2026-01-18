package com.upb.agripos;

import java.sql.Connection;
import java.sql.DriverManager;

import com.upb.agripos.controller.PosController;
import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.dao.ProductDAOImpl;
import com.upb.agripos.service.CartService;
import com.upb.agripos.service.ProductService;
import com.upb.agripos.view.PosView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppJavaFX extends Application {

    @Override
    public void start(Stage stage) {
        try {
            // Cetak identitas ke console (sesuai Bab 1)
            System.out.println("Hello World, I am Haidar Habibi Al Farisi-240202862");

            // 1. Setup Database Connection (Bab 11)
            Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/agripos", 
                "postgres", 
                "JERUKAGUNG"
            );

            // 2. Setup MVC + Service + DAO Architecture (Bab 6, 11, 12, 13)
            ProductDAO productDAO = new ProductDAOImpl(conn);
            ProductService productService = new ProductService(productDAO);
            CartService cartService = new CartService(productService);

            // 3. Create View (Bab 12-13)
            PosView view = new PosView();

            // 4. Create Controller (Bab 12-13)
            new PosController(productService, cartService, view);

            // 5. Display Scene
            Scene scene = new Scene(view, 1000, 800);
            stage.setTitle("Agri-POS Week 14 - Haidar Habibi Al Farisi (240202862)");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.err.println("Error starting application: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}