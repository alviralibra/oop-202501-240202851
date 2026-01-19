package com.upb.agripos.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class ProductFormView extends GridPane {
    // Komponen yang bisa diisi user
    public TextField txtCode = new TextField();
    public TextField txtName = new TextField();
    public TextField txtPrice = new TextField();
    public TextField txtStock = new TextField();
    public Button btnAdd = new Button("Tambah Produk");

    public ProductFormView() {
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(10);

        // Nyusun tampilan (Kolom 0 = Label, Kolom 1 = Input)
        this.add(new Label("Kode Produk:"), 0, 0);
        this.add(txtCode, 1, 0);
        
        this.add(new Label("Nama Produk:"), 0, 1);
        this.add(txtName, 1, 1);
        
        this.add(new Label("Harga:"), 0, 2);
        this.add(txtPrice, 1, 2);
        
        this.add(new Label("Stok:"), 0, 3);
        this.add(txtStock, 1, 3);
        
        this.add(btnAdd, 1, 4);
    }
}