package com.upb.agripos;

import java.sql.Connection;
import java.sql.DriverManager;

import com.upb.agripos.dao.ProductDAOImpl;
import com.upb.agripos.dao.ProductDao;
import com.upb.agripos.service.ProductService;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AppJavaFX extends Application {
    private ProductService productService;

    @Override
    public void start(Stage stage) {
        try {
            // Ganti "1234" dengan password database kamu
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/agripos", "postgres", "1010");
            ProductDao dao = new ProductDAOImpl(conn);
            productService = new ProductService(dao);

            TextField txtCode = new TextField(); txtCode.setPromptText("Kode Produk");
            TextField txtName = new TextField(); txtName.setPromptText("Nama Produk");
            TextField txtPrice = new TextField(); txtPrice.setPromptText("Harga");
            TextField txtStock = new TextField(); txtStock.setPromptText("Stok");
            Button btnAdd = new Button("Tambah Produk");
            ListView<String> listView = new ListView<>();

            btnAdd.setOnAction(e -> {
                try {
                    productService.addProduct(txtCode.getText(), txtName.getText(), 
                        Double.parseDouble(txtPrice.getText()), Integer.parseInt(txtStock.getText()));
                    listView.getItems().add(txtCode.getText() + " - " + txtName.getText() + " (Stok: " + txtStock.getText() + ")");
                    txtCode.clear(); txtName.clear(); txtPrice.clear(); txtStock.clear();
                    new Alert(Alert.AlertType.INFORMATION, "Berhasil simpan!").show();
                } catch (Exception ex) {
                    new Alert(Alert.AlertType.ERROR, "Error: " + ex.getMessage()).show();
                }
            });

            GridPane grid = new GridPane();
            grid.setPadding(new Insets(10)); grid.setVgap(10); grid.setHgap(10);
            grid.add(new Label("Kode:"), 0, 0); grid.add(txtCode, 1, 0);
            grid.add(new Label("Nama:"), 0, 1); grid.add(txtName, 1, 1);
            grid.add(new Label("Harga:"), 0, 2); grid.add(txtPrice, 1, 2);
            grid.add(new Label("Stok:"), 0, 3); grid.add(txtStock, 1, 3);
            grid.add(btnAdd, 1, 4);

            VBox root = new VBox(10, new Label("Agri-POS Week 12"), grid, listView);
            root.setPadding(new Insets(20));
            stage.setScene(new Scene(root, 400, 500));
            stage.setTitle("Agri-POS Alvira");
            stage.show();
        } catch (Exception e) { e.printStackTrace(); }
    }
    public static void main(String[] args) { launch(args); }
}