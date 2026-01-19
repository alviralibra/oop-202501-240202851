package com.upb.agripos.view;

import com.upb.agripos.model.Product;
import com.upb.agripos.service.ProductService;
import com.upb.agripos.service.CartService;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class PosView extends VBox {
    private TableView<Product> table = new TableView<>();
    private ProductService productService = new ProductService();
    private CartService cartService = new CartService();
    private Label lblTotal = new Label("Total Keranjang: Rp 0");

    public PosView() {
        System.out.println("Hello World, I am Alvira Libra - 240202851");

        TableColumn<Product, String> colName = new TableColumn<>("Nama Produk");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Product, Double> colPrice = new TableColumn<>("Harga");
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        table.getColumns().addAll(colName, colPrice);

        table.getItems().setAll(productService.listProducts());

        Button btnAddCart = new Button("Tambah ke Keranjang");
        btnAddCart.setOnAction(e -> {
            Product selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                try {
                    cartService.addItem(selected, 1);
                    lblTotal.setText("Total Keranjang: Rp " + cartService.calculateTotal());
                } catch (Exception ex) {
                    new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
                }
            }
        });

        this.setPadding(new Insets(20));
        this.setSpacing(10);
        this.getChildren().addAll(new Label("AGRI-POS ALVIRA LIBRA"), table, btnAddCart, lblTotal);
    }
}