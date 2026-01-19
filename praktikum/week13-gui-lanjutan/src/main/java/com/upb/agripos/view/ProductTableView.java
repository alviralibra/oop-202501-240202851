package com.upb.agripos.view;

import com.upb.agripos.controller.ProductController;
import com.upb.agripos.model.Product;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

public class ProductTableView extends VBox {
    private TableView<Product> table = new TableView<>();
    private ProductController controller = new ProductController();

    public ProductTableView() {
        this.setPadding(new Insets(10));
        this.setSpacing(10);

        // Kolom Tabel - Menggunakan "code" sesuai pgAdmin Alvira
        TableColumn<Product, Integer> codeCol = new TableColumn<>("Code/ID");
        codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));

        TableColumn<Product, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Product, Integer> stockCol = new TableColumn<>("Stock");
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        table.getColumns().addAll(codeCol, nameCol, priceCol, stockCol);
        refreshTable();

        // Form Input
        TextField nameField = new TextField();
        nameField.setPromptText("Product Name");
        TextField priceField = new TextField();
        priceField.setPromptText("Price");
        TextField stockField = new TextField();
        stockField.setPromptText("Stock");

        Button addButton = new Button("Add Product");
        addButton.setOnAction(e -> {
            try {
                controller.addProduct(
                    nameField.getText(),
                    Double.parseDouble(priceField.getText()),
                    Integer.parseInt(stockField.getText())
                );
                refreshTable();
                nameField.clear();
                priceField.clear();
                stockField.clear();
            } catch (Exception ex) {
                System.out.println("Error adding product: " + ex.getMessage());
            }
        });

        Button deleteButton = new Button("Delete Selected");
        deleteButton.setOnAction(e -> {
            Product selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                controller.deleteProduct(selected.getCode());
                refreshTable();
            }
        });

        HBox form = new HBox(10, nameField, priceField, stockField, addButton, deleteButton);
        this.getChildren().addAll(new Label("Agri-POS Alvira Libra - 240202851"), table, form);
    }

    private void refreshTable() {
        // Mengambil data terbaru dari database agripos_db
        table.setItems(FXCollections.observableArrayList(controller.getAllProducts()));
    }
}